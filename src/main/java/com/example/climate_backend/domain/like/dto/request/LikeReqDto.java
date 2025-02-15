package com.example.climate_backend.domain.like.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeReqDto {

    private Long userId;
    private Long postId;

    public LikeReqDto(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
    }
}
