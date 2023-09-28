package com.example.thuctap.services.account;

import com.example.thuctap.models.User;

import com.example.thuctap.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServices implements IUserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void saveOrUpdate(User user) {
        userRepository.save(user);
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    @Override
    public void updateResetPasswordToken(String token, String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        }
        else {
            throw new UsernameNotFoundException("Not found any user with email "+ email);
        }
    }

    @Override
    public User getPasswordResetToken(String token) {
        return userRepository.findUserByResetPasswordToken(token);
    }


//    @Override
//    public void createPasswordResetTokenForUser(User user, String token) {
//        PasswordResetToken myToken = new PasswordResetToken(token, user);
//
//        passwordTokenRepository.save(myToken);
//    }
//

//
//    @Override
//    public String validatePasswordResetToken(String token) {
//        final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
//
//        return !isTokenFound(passToken) ? "invalidToken"
//                : isTokenExpired(passToken) ? "expired"
//                : null;
//    }
//
//    @Override
//    public boolean isTokenFound(PasswordResetToken passwordResetToken) {
//        return passwordResetToken != null;
//    }
//
//    @Override
//    public boolean isTokenExpired(PasswordResetToken passwordResetToken) {
//        final Calendar cal = Calendar.getInstance();
//        return passwordResetToken.getExpiryDate().before(cal.getTime());
//    }
//
//    @Override
//    public void changeUserPassword(User user, String password) {
//        user.setPassword(passwordEncoder.encode(password));
//        userRepository.save(user);
//    }



}
