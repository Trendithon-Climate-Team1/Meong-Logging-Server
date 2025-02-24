package com.example.climate_backend.domain.post.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WritePostDto {
    private String content;
    private String location;
    private String userId;
}
