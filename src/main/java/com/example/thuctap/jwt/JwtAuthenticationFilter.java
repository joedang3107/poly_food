package com.example.thuctap.jwt;

import com.example.thuctap.security.CustomUserDetailsService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private String getJwtFromRequest (HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // kiem tra xem trong header authorization co chua jwt hay khong
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    //filter cac request xem co hop le hay khong de lay ra thong tin ve user do
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // lay jwt tu request
            String jwt = getJwtFromRequest(request);
            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)){
                //lay username tu chuoi jwt
                String userName = jwtTokenProvider.getUserNameFromJwt(jwt);
                // lay thong tin ng dung tu username
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
                if (userName!= null) {
                    // neu ng dung hop le set thong tin cho security context (lay ra quyen de sau nay phan quyen)
                    UsernamePasswordAuthenticationToken authenticationToken
                            = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }  catch (Exception ex) {
            log.error("fail on set user authentication", ex);
        }
        filterChain.doFilter(request, response);
    }
}
