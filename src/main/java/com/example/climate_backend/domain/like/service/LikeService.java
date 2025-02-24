package com.example.climate_backend.domain.like.service;

import com.example.climate_backend.domain.like.dto.request.LikeReqDto;
import com.example.climate_backend.domain.like.entity.Like;
import com.example.climate_backend.domain.like.repository.LikeRepository;
import com.example.climate_backend.domain.post.entity.Post;
import com.example.climate_backend.domain.post.repository.PostRepository;
import com.example.climate_backend.domain.user.entity.User;
import com.example.climate_backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public void insertLike(LikeReqDto likeReqDto) {
        User user = userRepository.findByUserId(likeReqDto.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다." + likeReqDto.getUserId()));

        Post post = postRepository.findById(likeReqDto.getPostId())
            .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다." + likeReqDto.getPostId()));

        if (likeRepository.findByUserAndPost(user, post).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 좋아요를 눌렀습니다.");
        }
        post.increaseLike();

        Like like = Like.builder()
            .user(user)
            .post(post)
            .build();

        likeRepository.save(like);
    }

    public int getAllLike(LikeReqDto likeReqDto) {
        Post post = postRepository.findById(likeReqDto.getPostId())
            .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다." + likeReqDto.getPostId()));

        return likeRepository.countByPost(post);
    }

    @Transactional
    public void deleteLike(LikeReqDto likeReqDto) {
        User user = userRepository.findByUserId(likeReqDto.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다." + likeReqDto.getUserId()));

        Post post = postRepository.findById(likeReqDto.getPostId())
            .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다." + likeReqDto.getPostId()));

        Like like = likeRepository.findByUserAndPost(user, post)
            .orElseThrow(() -> new IllegalArgumentException("좋아요를 찾을 수 없습니다."));

        post.decreaseLike();

        likeRepository.delete(like);
    }

}
