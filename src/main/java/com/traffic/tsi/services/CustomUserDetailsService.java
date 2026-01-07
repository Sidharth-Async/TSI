package com.traffic.tsi.services;

import com.traffic.tsi.entities.CustomUserDetails;
import com.traffic.tsi.entities.User;
import com.traffic.tsi.repos.user_Repo;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final user_Repo userRepository;

    public CustomUserDetailsService(user_Repo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Spring Security calls this method with whatever the user enters in the login form
        // In your case, users enter their email, so we treat 'username' parameter as email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new CustomUserDetails(user);
    }
}