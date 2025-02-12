package com.example.climate_backend.domain.post.controller;

import com.example.climate_backend.domain.post.dto.request.WritePostDto;
import com.example.climate_backend.domain.post.dto.response.PostResponseDto;
import com.example.climate_backend.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<String> writePost(@RequestPart(value = "post") WritePostDto writePostDto, @RequestPart(required = false) MultipartFile file){
        postService.writePost(writePostDto, file);
        return ResponseEntity.ok("게시글 작성이 완료되었습니다.");
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getPostList(){
        return ResponseEntity.ok(postService.getPostList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

}
