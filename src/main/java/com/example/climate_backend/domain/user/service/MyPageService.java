package com.example.climate_backend.domain.user.service;

import com.example.climate_backend.domain.post.dto.response.PostResponseDto;
import com.example.climate_backend.domain.post.entity.Post;
import com.example.climate_backend.domain.user.dto.request.MyPageReqDto;
import com.example.climate_backend.domain.user.dto.response.MyPageResDto;
import com.example.climate_backend.domain.user.entity.User;
import com.example.climate_backend.domain.user.repository.UserRepository;
import com.example.climate_backend.domain.verification.dto.response.VerificationResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;

    public MyPageResDto getMyPage(String userId) {
        User user = findExistingUserByUserId(userId);

        return MyPageResDto.builder()
            .userId(user.getUserId())
            .email(user.getEmail())
            .nickname(user.getNickname())
            .petName(user.getPetName())
            .profileImg(user.getProfileImg())
            .build();
    }

    @Transactional
    public MyPageResDto updateMyPage(String userId, MyPageReqDto myPageReqDto) {
        User user = findExistingUserByUserId(userId);
        user.update(myPageReqDto);
        return MyPageResDto.builder()
            .userId(user.getUserId())
            .email(user.getEmail())
            .nickname(user.getNickname())
            .petName(user.getPetName())
            .profileImg(user.getProfileImg())
            .build();
    }

    public MyPageResDto updateProfileImage(String userId, MyPageReqDto myPageReqDto) {
        User user = findExistingUserByUserId(userId);
        user.updateProfileImage(myPageReqDto.getProfileImg());

        return MyPageResDto.builder()
            .userId(user.getUserId())
            .email(user.getEmail())
            .nickname(user.getNickname())
            .petName(user.getPetName())
            .profileImg(user.getProfileImg())
            .build();
    }

    public List<PostResponseDto> getMyPosts(String userId) {
        User user = findExistingUserByUserId(userId);
        return user.getPosts().stream()
            .map(PostResponseDto::new)
            .collect(Collectors.toList());
    }

    public List<VerificationResponseDto> getMyLogs(String userId) {
        User user = findExistingUserByUserId(userId);
        return user.getVerifications().stream()
            .map(VerificationResponseDto::fromEntity)
            .collect(Collectors.toList());
    }

    private User findExistingUserByUserId(String userId) {
        return userRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));
    }

}
