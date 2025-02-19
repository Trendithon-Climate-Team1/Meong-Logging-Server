package com.example.climate_backend.domain.post.service;

import com.example.climate_backend.domain.post.dto.request.UpdatePostDto;
import com.example.climate_backend.domain.post.dto.request.WritePostDto;
import com.example.climate_backend.domain.post.dto.response.PostResponseDto;
import com.example.climate_backend.domain.post.entity.Post;
import com.example.climate_backend.domain.post.repository.PostRepository;
import com.example.climate_backend.domain.user.entity.User;
import com.example.climate_backend.domain.user.repository.UserRepository;
import com.example.climate_backend.domain.user.service.UserService;
import com.example.climate_backend.global.common.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final UserService userService;

    public void writePost(WritePostDto writePostDto, MultipartFile file) {
        User user = userRepository.findById(writePostDto.getUserId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
        String imgUrl = null;
        if (file != null) imgUrl = s3Service.uploadImage(file);
        Post post = Post.builder()
                .content(writePostDto.getContent())
                .location(writePostDto.getLocation())
                .imageUrl(imgUrl)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();
        postRepository.save(post);
    }

    public void updatePost(Long id, UpdatePostDto updatePostDto) {
        User user = userRepository.findById(updatePostDto.getUserId())
                .orElseThrow(()-> new RuntimeException("존재하지 않는 유저입니다."));
        Post post = findPostById(id);
        if(post.getUser() != user)
            throw new RuntimeException("게시글 수정 권한이 없습니다.");
        post.update(updatePostDto);
        postRepository.save(post);
    }

    public PostResponseDto getPostById(Long postId) {
        Post post = findPostById(postId);
        return new PostResponseDto(post);
    }

    public Post findPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));
    }

    public List<PostResponseDto> getPostList() {
        return postRepository.findAll().stream()
                .map(PostResponseDto::new)
                .toList();
    }

}
