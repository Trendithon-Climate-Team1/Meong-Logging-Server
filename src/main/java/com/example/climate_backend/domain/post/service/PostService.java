package com.example.climate_backend.domain.post.service;

import com.example.climate_backend.domain.post.dto.request.WritePostDto;
import com.example.climate_backend.domain.post.dto.response.PostResponseDto;
import com.example.climate_backend.domain.post.entity.Post;
import com.example.climate_backend.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void writePost(WritePostDto writePostDto, MultipartFile file) {
        Post post = Post.builder()
                .content(writePostDto.getContent())
                .location(writePostDto.getLocation())
                .createdAt(LocalDateTime.now())
                .build();
        postRepository.save(post);
    }

    public PostResponseDto getPostById(Long postId) {
        Post post = findPostById(postId);
        return new PostResponseDto(post);
    }

    public Post findPostById(Long id){
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));
    }

    public List<PostResponseDto> getPostList() {
        return postRepository.findAll().stream()
                .map(PostResponseDto::new)
                .toList();
    }
}
