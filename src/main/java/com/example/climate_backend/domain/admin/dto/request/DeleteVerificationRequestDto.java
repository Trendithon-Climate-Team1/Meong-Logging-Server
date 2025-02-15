package com.example.climate_backend.domain.admin.dto.request;

import lombok.Data;

@Data
public class DeleteVerificationRequestDto {
    private Long verificationId;
    private String adminUserId;
}
