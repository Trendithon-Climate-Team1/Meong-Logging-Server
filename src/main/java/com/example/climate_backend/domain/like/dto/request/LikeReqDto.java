package com.example.climate_backend.domain.like.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeReqDto {

    private String userId;
    private Long postId;

    public LikeReqDto(String userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
    }
}
