package com.example.climate_backend.domain.comment.dto.response;

import com.example.climate_backend.domain.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResDto {
    private String comment;
    private String petName;
    private String userId;
    private Long postId;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public CommentResDto(Comment comment) {
        this.comment = comment.getComment();
        this.petName = comment.getUser().getPetName();
        this.userId = comment.getUser().getUserId();
        this.postId = comment.getPost().getId();
        this.createdAt = comment.getCreatedAt();
        this.updateAt = comment.getUpdatedAt();
    }
}
