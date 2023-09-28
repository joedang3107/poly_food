package com.example.thuctap.security;

import com.example.thuctap.models.User;
import com.example.thuctap.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User account = userRepository.findByUserName(username);

        if (account == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return CustomUserDetails.mapAccountDetailsToUserDetails(account);
    }
}
