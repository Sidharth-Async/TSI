package com.traffic.tsi.controllers;

import com.traffic.tsi.dtos.JwtAuthResponse;
import com.traffic.tsi.dtos.Signin;
import com.traffic.tsi.dtos.Signup;
import com.traffic.tsi.entities.User;
import com.traffic.tsi.repos.user_Repo;
import com.traffic.tsi.services.user_Service;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class user_Controller {

    private final user_Repo repository;
    private final user_Service service;

    public user_Controller(user_Repo repository, user_Service service){
        this.repository = repository;
        this.service = service;
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<Signup> createUser(@RequestBody Signup signup){
        Signup savedUser = service.register(signup);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/auth/signup")
    public List<User> getAllUsers(){
        return service.getAllUsers();
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<?> login(@RequestBody Signin signin) {
        try {
            // ONE LINE: Call the method above
            String token = service.verify(signin);

            // Return the response
            return ResponseEntity.ok(new JwtAuthResponse(token));

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
}
