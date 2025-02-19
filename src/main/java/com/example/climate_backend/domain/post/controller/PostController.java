package com.example.climate_backend.domain.post.controller;

import com.example.climate_backend.domain.post.dto.request.UpdatePostDto;
import com.example.climate_backend.domain.post.dto.request.WritePostDto;
import com.example.climate_backend.domain.post.dto.response.PostResponseDto;
import com.example.climate_backend.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@Tag(name = "Post API", description = "커뮤니티 게시글 CRUD")
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시글 작성")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> writePost(@RequestPart(value = "post") WritePostDto writePostDto, @RequestPart(required = false, value = "file") MultipartFile file){
        postService.writePost(writePostDto, file);
        return ResponseEntity.ok("게시글 작성이 완료되었습니다.");
    }

    @Operation(summary = "모든 게시글 조회")
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getPostList(){
        return ResponseEntity.ok(postService.getPostList());
    }

    @Operation(summary = "게시글 상세 조회")
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @Operation(summary = "게시글 수정")
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@PathVariable Long id, @RequestBody UpdatePostDto updatePostDto){
        postService.updatePost(id, updatePostDto);
        return ResponseEntity.ok("게시글이 수정되었습니다.");

    }

}
