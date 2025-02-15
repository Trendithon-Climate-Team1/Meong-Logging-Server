package com.example.climate_backend.domain.comment.dto.response;

import com.example.climate_backend.domain.comment.entity.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResDto {
    private String comment;
    private Long userId;
    private Long postId;

    public CommentResDto(Comment comment) {
        this.comment = comment.getComment();
        this.userId = comment.getUser().getId();
        this.postId = comment.getPost().getId();
    }
}
