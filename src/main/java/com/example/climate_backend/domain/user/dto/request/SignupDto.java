package com.example.climate_backend.domain.user.dto.request;

import lombok.Getter;

@Getter
public class SignupDto {

    private String userId;
    private String password;
    private String email;
    private String nickname;
}
