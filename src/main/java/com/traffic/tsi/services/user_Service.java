package com.traffic.tsi.services;

import com.traffic.tsi.dtos.Signin;
import com.traffic.tsi.dtos.Signup;
import com.traffic.tsi.entities.User;

import java.util.List;

public interface user_Service {
    Signup register(Signup signup);
    List<User> getAllUsers();
    String verify(Signin signin);
}
