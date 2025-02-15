package com.example.climate_backend.domain.post.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WritePostDto {
    private String content;
    private String location;
    private Long userId;
}
