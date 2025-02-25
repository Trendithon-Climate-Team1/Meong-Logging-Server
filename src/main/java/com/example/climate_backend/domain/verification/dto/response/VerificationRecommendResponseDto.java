package com.example.climate_backend.domain.verification.dto.response;

import com.example.climate_backend.domain.verification.entity.Coordinate;
import com.example.climate_backend.domain.verification.entity.Verification;
import com.example.climate_backend.domain.verification.enums.VerificationStatus;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class VerificationRecommendResponseDto {
    private String userId;
    private String petName;
    private String courseName;
    private String date;
    private List<Coordinate> path;
    private String captureImage;
    private VerificationStatus verificationStatus;
    private Long verificationId;

    public static VerificationRecommendResponseDto fromEntity(Verification verification) {
        return VerificationRecommendResponseDto.builder()
                .userId(verification.getUser().getUserId())
                .petName(verification.getUser().getPetName())
                .courseName(verification.getCourseName())
                .date(verification.getDate())
                .path(verification.getPath())
                .captureImage(verification.getCaptureImage())
                .verificationStatus(verification.getStatus())
                .verificationId(verification.getId())
                .build();
    }
}
