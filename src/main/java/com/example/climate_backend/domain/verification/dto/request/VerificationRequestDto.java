package com.example.climate_backend.domain.verification.dto.request;

import com.example.climate_backend.domain.verification.entity.Coordinate;
import lombok.Data;

import java.util.List;

@Data
public class VerificationRequestDto {
    private String userId;
    private String courseName;
    private String date;
    private List<Coordinate> path;
}
