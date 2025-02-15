package com.example.climate_backend.domain.admin.service;

import com.example.climate_backend.domain.admin.dto.request.AdminVerificationRequestDto;
import com.example.climate_backend.domain.admin.dto.request.DeleteVerificationRequestDto;
import com.example.climate_backend.domain.user.entity.User;
import com.example.climate_backend.domain.user.enums.Role;
import com.example.climate_backend.domain.user.repository.UserRepository;
import com.example.climate_backend.domain.verification.entity.Verification;
import com.example.climate_backend.domain.verification.enums.VerificationStatus;
import com.example.climate_backend.domain.verification.repository.VerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final VerificationRepository verificationRepository;
    private final UserRepository userRepository;

    @Transactional
    public void processVerification(AdminVerificationRequestDto requestDto) {
        User admin = validateAdmin(requestDto.getAdminUserId());
        Verification verification = findVerificationById(requestDto.getVerificationId());

        if (requestDto.getStatus() == VerificationStatus.APPROVED) {
            verification.approve();
        } else if (requestDto.getStatus() == VerificationStatus.REJECTED) {
            verification.reject();
        } else {
            throw new IllegalArgumentException("잘못된 인증 상태값입니다.");
        }

        verificationRepository.save(verification);
    }

    @Transactional
    public void deleteVerification(DeleteVerificationRequestDto requestDto) {
        User admin = validateAdmin(requestDto.getAdminUserId());
        Verification verification = findVerificationById(requestDto.getVerificationId());

        verificationRepository.delete(verification);
    }


    private User validateAdmin(String adminUserId) {
        User admin = userRepository.findByUserId(adminUserId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 관리자 계정입니다."));

        if (admin.getRole() != Role.ROLE_ADMIN) {
            throw new RuntimeException("관리자 권한이 없습니다.");
        }
        return admin;
    }

    private Verification findVerificationById(Long verificationId) {
        return verificationRepository.findById(verificationId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 인증 요청입니다."));
    }
}
