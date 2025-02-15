package com.example.climate_backend.domain.admin.dto.request;

import com.example.climate_backend.domain.verification.enums.VerificationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminVerificationRequestDto {
    private Long verificationId;
    private String adminUserId;
    private VerificationStatus status;
}
