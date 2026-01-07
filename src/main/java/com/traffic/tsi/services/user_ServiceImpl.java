package com.traffic.tsi.services;


import com.traffic.tsi.dtos.Signin;
import com.traffic.tsi.dtos.Signup;
import com.traffic.tsi.entities.User;
import com.traffic.tsi.enums.user_Role;
import com.traffic.tsi.repos.user_Repo;
import com.traffic.tsi.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Set;

@Service
public class user_ServiceImpl implements user_Service {

    private final user_Repo repository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider  jwtTokenProvider;

    public user_ServiceImpl(user_Repo repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    // Signup method
    @Override
    public Signup register(Signup signup) {

        if (repository.existsByUsername(signup.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (repository.existsByEmail(signup.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Create User entity
        User user = new User();

        user.setUsername(signup.getUsername());
        user.setEmail(signup.getEmail());
        user.setPassword(passwordEncoder.encode(signup.getPassword()));
        user.setRoles(Set.of(user_Role.ADMIN, user_Role.CITY_PLANNER));

        // Save to DB
        User savedUser = repository.save(user);

        return mapToSignup(savedUser);
    }

    private Signup mapToSignup(User user) {
        Signup signup = new Signup();

        // FIX IS HERE: Use setUsername (Standard), not setUser_name
        signup.setUsername(user.getUsername());

        signup.setEmail(user.getEmail());

        // Note: We intentionally do NOT set the password here.
        // It's safer to return 'null' than to send the encrypted password back to the user.

        return signup;
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    //login method starts here...

    @Override
    public String verify(Signin signin) {
        // 1. YOUR AUTHENTICATION LOGIC
        // (This is exactly what you wrote, just moved here)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signin.getEmail(),
                        signin.getPassword()
                )
        );

        // 2. YOUR CONTEXT LOGIC (Optional but fine to keep)
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. YOUR TOKEN GENERATION LOGIC
        String token = jwtTokenProvider.generateToken(authentication);

        // 4. RETURN THE TOKEN
        return token;
    }
}