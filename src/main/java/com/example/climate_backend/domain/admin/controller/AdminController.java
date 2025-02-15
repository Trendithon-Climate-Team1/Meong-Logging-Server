package com.example.climate_backend.domain.admin.controller;


import com.example.climate_backend.domain.admin.dto.request.AdminVerificationRequestDto;
import com.example.climate_backend.domain.admin.dto.request.DeleteVerificationRequestDto;
import com.example.climate_backend.domain.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin API", description = "관리자용 플로깅 인증 관리 API")
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "인증 승인 또는 거절")
    @PostMapping("/verification")
    public ResponseEntity<String> processVerification(@RequestBody AdminVerificationRequestDto requestDto) {
        adminService.processVerification(requestDto);
        return ResponseEntity.ok("플로깅 인증이 " + requestDto.getStatus() + " 상태로 변경되었습니다.");
    }

    @Operation(summary = "인증 삭제")
    @DeleteMapping("/verification")
    public ResponseEntity<String> deleteVerification(@RequestBody DeleteVerificationRequestDto requestDto) {
        adminService.deleteVerification(requestDto);
        return ResponseEntity.ok("인증 요청이 삭제되었습니다.");
    }
}
