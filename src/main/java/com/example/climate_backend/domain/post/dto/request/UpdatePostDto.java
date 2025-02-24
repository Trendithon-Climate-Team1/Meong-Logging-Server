package com.example.climate_backend.domain.post.dto.request;

import lombok.Getter;

@Getter
public class UpdatePostDto {
    private String content;
    private String userId;
}
