package com.example.climate_backend.domain.user.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String hashPassword(String password){
        return passwordEncoder.encode(password);
    }

    public boolean matchPassword(String password, String encodedPassword){
        return passwordEncoder.matches(password, encodedPassword);
    }

}