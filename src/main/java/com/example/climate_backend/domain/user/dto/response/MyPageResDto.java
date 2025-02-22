package com.example.climate_backend.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyPageResDto {
    private String userId;
    private String password;
    private String email;
    private String nickname;
    private String petName;
    private int point;
    private String profileImg;
}
