package com.example.climate_backend.domain.user.dto.request;

import lombok.Getter;

@Getter
public class LoginDto {

    private String userId;
    private String password;
}
