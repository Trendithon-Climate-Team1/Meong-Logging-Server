package com.example.climate_backend.domain.like.controller;

import com.example.climate_backend.domain.like.dto.request.LikeReqDto;
import com.example.climate_backend.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<String> insertLike(@RequestBody LikeReqDto likeReqDto) {
        likeService.insertLike(likeReqDto);
        return ResponseEntity.ok("좋아요가 등록되었습니다.");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteLike(@RequestBody LikeReqDto likeReqDto) {
        likeService.deleteLike(likeReqDto);
        return ResponseEntity.ok("좋아요가 취소되었습니다.");
    }

}
