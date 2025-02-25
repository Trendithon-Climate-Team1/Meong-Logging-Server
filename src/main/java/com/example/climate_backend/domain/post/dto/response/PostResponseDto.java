package com.example.climate_backend.domain.post.dto.response;

import com.example.climate_backend.domain.like.repository.LikeRepository;
import com.example.climate_backend.domain.like.service.LikeService;
import com.example.climate_backend.domain.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private String content;
    private String location;
    private String imageUrl;
    private String petName;
    private int likeCount;
    private LocalDateTime createdAt;
    private Long userId;

    public PostResponseDto(Post post){
        this.id = post.getId();
        this.content = post.getContent();
        this.location = post.getLocation();
        this.imageUrl = post.getImageUrl();
        this.petName = post.getUser().getPetName();
        this.likeCount = post.getLikeCount();
        this.userId = post.getUser().getId();
        this.createdAt = post.getCreatedAt();
    }

}
