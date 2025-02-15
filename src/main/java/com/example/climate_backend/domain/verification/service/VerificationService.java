package com.example.climate_backend.domain.verification.service;

import com.example.climate_backend.domain.user.entity.User;
import com.example.climate_backend.domain.user.repository.UserRepository;
import com.example.climate_backend.domain.verification.dto.request.VerificationRequestDto;
import com.example.climate_backend.domain.verification.dto.response.VerificationResponseDto;
import com.example.climate_backend.domain.verification.entity.Verification;
import com.example.climate_backend.domain.verification.enums.VerificationStatus;
import com.example.climate_backend.domain.verification.repository.VerificationRepository;
import com.example.climate_backend.global.common.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationService {

    private final VerificationRepository verificationRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    public void saveVerification(VerificationRequestDto verificationDto, MultipartFile file) {
        User user = findExistingUserByUserId(verificationDto.getUserId());

        String imageUrl=null;
        if (file != null) imageUrl = s3Service.uploadImage(file);



        Verification verification = Verification.createFromDto(verificationDto,imageUrl, user);
        verificationRepository.save(verification);
    }

    public List<VerificationResponseDto> getVerificationsByUser(String userId) {
        User user = findExistingUserByUserId(userId);

        return verificationRepository.findByUser(user).stream()
                .map(VerificationResponseDto::fromEntity)
                .collect(Collectors.toList());
    }


    public List<VerificationResponseDto> getVerificationsByStatus(VerificationStatus status) {
        log.info("status : ",status);
        List<Verification> verifications;

        // status가 null이면 전체 조회, 아니면 특정 상태만 조회
        if (status == null) {
            verifications = verificationRepository.findAll();
        } else {
            verifications = verificationRepository.findByStatus(status);
        }

        return verifications.stream()
                .map(VerificationResponseDto::fromEntity)
                .collect(Collectors.toList());
    }



    public VerificationResponseDto getVerificationById(Long verificationId) {
        Verification verification = verificationRepository.findById(verificationId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 인증 요청입니다."));
        return VerificationResponseDto.fromEntity(verification);
    }

    private User findExistingUserByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));
    }
}
