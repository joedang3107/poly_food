package com.example.thuctap.controller;

import com.example.thuctap.helpers.Utility;
import com.example.thuctap.jwt.JwtTokenProvider;
import com.example.thuctap.models.User;
import com.example.thuctap.models.ERole;
import com.example.thuctap.models.Role;
import com.example.thuctap.payload.request.LoginRequest;
import com.example.thuctap.payload.request.SignupRequest;
import com.example.thuctap.payload.response.JwtResponse;
import com.example.thuctap.payload.response.MessageResponse;
import com.example.thuctap.security.CustomUserDetails;
import com.example.thuctap.services.account.UserServices;
import com.example.thuctap.services.decentralization.RoleServices;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserServices userServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RoleServices roleServices;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment environment;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping(value = "/register")
    public ResponseEntity<?> registerAccount(@Valid @RequestBody SignupRequest signupRequest) {

        if (userServices.existsByUserName(signupRequest.getUserName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userServices.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already taken!"));
        }

        User user = new User();

        user.setUserName(signupRequest.getUserName());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());
        user.setPhone(signupRequest.getPhone());
        user.setStatus(1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date now = new Date();
        String strNow = simpleDateFormat.format(now);
        try {
            user.setCreated_at(simpleDateFormat.parse(strNow));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Set<String> roles = signupRequest.getRoles();
        Set<Role> roleList = new HashSet<>();

        if (roles == null) {
            Role userRole = roleServices.findByRoleName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: role is not found"));
            roleList.add(userRole);
        } else {
            roles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleServices.findByRoleName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: role is not found"));
                        roleList.add(adminRole);
                    case "moderator":
                        Role moderatorRole = roleServices.findByRoleName(ERole.ROLE_MODERATOR).orElseThrow(() -> new RuntimeException("Error: role is not found"));
                        roleList.add(moderatorRole);
                    case "user":
                        Role userRole = roleServices.findByRoleName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: role is not found"));
                        roleList.add(userRole);
                }
            });
        }
        user.setRoles(roleList);
        userServices.saveOrUpdate(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


    @PostMapping(value = "/login")
    public ResponseEntity<?> loginAccount(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String jwt = jwtTokenProvider.generateToken(customUserDetails);

        Set<String> roles = customUserDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toSet());

        return ResponseEntity.ok(new JwtResponse(jwt,
                customUserDetails.getUsername(),
                customUserDetails.getPhone(),
                customUserDetails.getEmail(),
                roles));

    }

    @PostMapping(value = "/forgot_password")
    public String processForgotPasswordPage(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(45);
        try {
            userServices.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteUrl(request) + "/request_password?token=" + token;

            sendEmail(email, resetPasswordLink);

            model.addAttribute("message", "We have sent a reset password link to your email, please check");
        }
        catch (Exception exception) {
            model.addAttribute("error", exception.getMessage());
        }
        return "forgot_password_form";
    }

    // kiem tra password_reset_token co dung hay khong
    @GetMapping(value = "/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {

        User user = userServices.getPasswordResetToken(token);

        if (user == null) {
            model.addAttribute("title", "Reset your password");
            model.addAttribute("message", "Invalid token");
            return "message";
        }
        model.addAttribute("token", token);
        model.addAttribute("pageTitle", "Reset your password");
        return "reset_password_form";
    }

    // doi password moi
    @PostMapping(value = "/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String newPassword = request.getParameter("newPassword");
        String token = request.getParameter("token");

        User user = userServices.getPasswordResetToken(token);
        if (user == null) {
            model.addAttribute("title", "Reset your password");
            model.addAttribute("message", "Invalid token");
        }
        else {
            userServices.updatePassword(user, newPassword);
            model.addAttribute("message", "You have successfully changed your password");
        }

        return "reset_password_form";
    }

    private void sendEmail(String email, String resetPasswordLink) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("thienmoxyz@gmail.com", "Polyfood support");
        helper.setTo(email);

        String subject = "Here the link to reset your password";
        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password</p>"
                + "<p>Click the link below to change your password</p>"
                + "<p><b><a href=\"" + resetPasswordLink + "\">Change my password</a></b></p>"
                +"<p>Ignore this email if you have not made the request</p>";
        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(message);

    }
}
