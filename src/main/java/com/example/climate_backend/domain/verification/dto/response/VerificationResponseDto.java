package com.example.climate_backend.domain.verification.dto.response;

import com.example.climate_backend.domain.verification.entity.Coordinate;
import com.example.climate_backend.domain.verification.entity.Verification;
import com.example.climate_backend.domain.verification.enums.VerificationStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Builder
@Data
public class VerificationResponseDto {
    private String userId;
    private String courseName;
    private String date;
    private List<Coordinate> path;
    private List<String> uploadedImages;
    private VerificationStatus verificationStatus;
    private Long verificationId;

    public static VerificationResponseDto fromEntity(Verification verification) {
        return VerificationResponseDto.builder()
                .userId(verification.getUser().getUserId())
                .courseName(verification.getCourseName())
                .date(verification.getDate())
                .path(verification.getPath())
                .uploadedImages(verification.getUploadedImages())
                .verificationStatus(verification.getStatus())
                .verificationId(verification.getId())
                .build();
    }
}
