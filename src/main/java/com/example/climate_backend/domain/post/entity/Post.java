package com.example.climate_backend.domain.post.entity;

import com.example.climate_backend.domain.post.dto.request.UpdatePostDto;
import com.example.climate_backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String imageUrl;
    private String location;
    private int likeCount;
    private int commentCount;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void increaseLike() {
        this.likeCount++;
    }

    public void decreaseLike() {
        this.likeCount--;
    }

    public void increaseComment() {
        this.commentCount++;
    }

    public void decreaseComment() {
        this.commentCount--;
    }

    public void update(UpdatePostDto updatePostDto) {
        this.content = updatePostDto.getContent();
    }
}

