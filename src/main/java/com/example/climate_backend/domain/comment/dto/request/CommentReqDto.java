package com.example.climate_backend.domain.comment.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentReqDto {
    private String comment;
    private String userId;
    private Long postId;

    public CommentReqDto(String comment, String userId, Long postId) {
        this.comment = comment;
        this.userId = userId;
        this.postId = postId;
    }
}
