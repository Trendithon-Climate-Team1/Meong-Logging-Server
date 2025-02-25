package com.example.climate_backend.domain.user.service;

import com.example.climate_backend.domain.post.dto.response.PostResponseDto;
import com.example.climate_backend.domain.post.entity.Post;
import com.example.climate_backend.domain.user.dto.request.MyPageReqDto;
import com.example.climate_backend.domain.user.dto.response.MyPageResDto;
import com.example.climate_backend.domain.user.entity.User;
import com.example.climate_backend.domain.user.repository.UserRepository;
import com.example.climate_backend.domain.verification.dto.request.VerificationRequestDto;
import com.example.climate_backend.domain.verification.dto.request.VerificationUpdateRequestDto;
import com.example.climate_backend.domain.verification.dto.response.VerificationResponseDto;
import com.example.climate_backend.domain.verification.entity.Verification;
import com.example.climate_backend.domain.verification.repository.VerificationRepository;
import com.example.climate_backend.global.common.S3Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final VerificationRepository verificationRepository;

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
            .build();
    }

    @Transactional
    public void updateProfileImage(String userId, MultipartFile file) {
        User user = findExistingUserByUserId(userId);

        if (user.getProfileImg() != null) {
            s3Service.deleteImage(user.getProfileImg());
        }

        user.updateProfileImage(s3Service.uploadImage(file));
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

    @Transactional
    public VerificationResponseDto updateMyLogs(String userId, Long verificationId, VerificationUpdateRequestDto verificationUpdateRequestDto) {
        User user = findExistingUserByUserId(userId);
        Verification verification =findExistingVerificationById(verificationId);

        verification.update(verificationUpdateRequestDto);
        return VerificationResponseDto.fromEntity(verification);
    }

    @Transactional
    public void deleteMyLogs(String userId, Long verificationId) {
        User user = findExistingUserByUserId(userId);
        Verification verification = findExistingVerificationById(verificationId);
        verificationRepository.delete(verification);
    }

    private User findExistingUserByUserId(String userId) {
        return userRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));
    }

    private Verification findExistingVerificationById(Long verificationId) {
        return verificationRepository.findById(verificationId)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 멍로깅입니다."));
    }

}
