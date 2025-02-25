package com.example.climate_backend.domain.verification.service;

import com.example.climate_backend.domain.user.entity.User;
import com.example.climate_backend.domain.user.repository.UserRepository;
import com.example.climate_backend.domain.verification.dto.request.VerificationRequestDto;
import com.example.climate_backend.domain.verification.dto.response.VerificationRecommendResponseDto;
import com.example.climate_backend.domain.verification.dto.response.VerificationResponseDto;
import com.example.climate_backend.domain.verification.entity.Verification;
import com.example.climate_backend.domain.verification.enums.VerificationStatus;
import com.example.climate_backend.domain.verification.repository.VerificationRepository;
import com.example.climate_backend.global.common.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationService {

    private final VerificationRepository verificationRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    public void saveVerification(VerificationRequestDto verificationDto, MultipartFile[] files) {
        User user = findExistingUserByUserId(verificationDto.getUserId());

        List<String> imageUrls = new ArrayList<>();
        if (files != null) {
            for (MultipartFile file : files) {
                try {
                    imageUrls.add(s3Service.uploadImage(file)); // 다중 이미지 업로드
                } catch (Exception e) {
                    throw new RuntimeException("이미지 업로드 실패", e);
                }
            }
        }

        Verification verification = Verification.createFromDto(verificationDto, imageUrls, user);
        verificationRepository.save(verification);
    }

    public List<VerificationResponseDto> getVerificationsByUser(String userId) {
        User user = findExistingUserByUserId(userId);
        return verificationRepository.findByUser(user).stream()
                .map(VerificationResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<VerificationResponseDto> getVerificationsByStatus(VerificationStatus status) {
        log.info("status : {}", status);
        List<Verification> verifications = (status == null) ? verificationRepository.findAll()
                : verificationRepository.findByStatus(status);

        return verifications.stream()
                .map(VerificationResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public VerificationResponseDto getVerificationById(Long verificationId) {
        Verification verification = verificationRepository.findById(verificationId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 인증 요청입니다."));
        return VerificationResponseDto.fromEntity(verification);
    }
    public void saveRecommend(Long verificationId, MultipartFile file) {
        Verification verification = verificationRepository.findById(verificationId)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 인증 요청입니다."));

        if (file != null) {
            String imageUrl = s3Service.uploadImage(file);
            verification.uploadCaptureImage(imageUrl);
            verificationRepository.save(verification);
        } else {
            throw new RuntimeException("이미지가 필요합니다.");
        }
    }


    public List<VerificationRecommendResponseDto> getRecommend(Long verificationId) {
        Verification verification = verificationRepository.findById(verificationId)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 인증 요청입니다."));
        List<Verification> recommendations = List.of(verification);

        return recommendations.stream()
            .map(VerificationRecommendResponseDto::fromEntity)
            .collect(Collectors.toList());
    }

    private User findExistingUserByUserId(String userId) {
        return userRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));
    }

}

