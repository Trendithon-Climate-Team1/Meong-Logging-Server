package com.example.climate_backend.domain.verification.controller;

import com.example.climate_backend.domain.verification.dto.request.VerificationRequestDto;
import com.example.climate_backend.domain.verification.dto.response.VerificationRecommendResponseDto;
import com.example.climate_backend.domain.verification.dto.response.VerificationResponseDto;
import com.example.climate_backend.domain.verification.enums.VerificationStatus;
import com.example.climate_backend.domain.verification.service.VerificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/verification")
@RequiredArgsConstructor
@Tag(name = "Verification API", description = "플로깅 인증 관련 API")
public class VerificationController {

    private final VerificationService verificationService;

    @Operation(summary = "플로깅 인증 저장")
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<String> saveVerification(
            @RequestPart(value = "verification") VerificationRequestDto verificationDto,
            @RequestPart(value = "files", required = false) MultipartFile[] files) {
        verificationService.saveVerification(verificationDto, files);
        return ResponseEntity.ok("플로깅 인증이 저장되었습니다.");
    }

    @Operation(summary = "사용자별 플로깅 인증 목록 조회")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<VerificationResponseDto>> getVerificationsByUser(@PathVariable String userId) {
        return ResponseEntity.ok(verificationService.getVerificationsByUser(userId));
    }

    @Operation(summary = "id 기반 특정 플로깅 인증 조회")
    @GetMapping("/{verificationId}")
    public ResponseEntity<VerificationResponseDto> getVerificationById(@PathVariable Long verificationId) {
        return ResponseEntity.ok(verificationService.getVerificationById(verificationId));
    }

    @Operation(summary = "상태별 플로깅 인증 목록 조회")
    @GetMapping
    public ResponseEntity<List<VerificationResponseDto>> getVerificationsByStatus(
            @RequestParam(required = false) VerificationStatus status) {
        return ResponseEntity.ok(verificationService.getVerificationsByStatus(status));
    }

    @Operation(summary = "플로깅 캡처본 저장(추천)")
    @PostMapping("/recommend/{verificationId}")
    public ResponseEntity<String> saveRecommend(
        @PathVariable Long verificationId,
        @RequestPart(value = "files") MultipartFile file) {
        verificationService.saveRecommend(verificationId, file);
        return ResponseEntity.ok("플로깅 캡처본이 저장되었습니다.");
    }

    @Operation(summary = "플로깅 추천 조회")
    @GetMapping("recommend/{verificationId}")
    public ResponseEntity<List<VerificationRecommendResponseDto>> getRecommend(@PathVariable Long verificationId) {
        return ResponseEntity.ok(verificationService.getRecommend(verificationId));
    }

}
