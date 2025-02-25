package com.example.climate_backend.domain.user.controller;

import com.example.climate_backend.domain.post.dto.response.PostResponseDto;
import com.example.climate_backend.domain.user.dto.request.MyPageReqDto;
import com.example.climate_backend.domain.user.dto.response.MyPageResDto;
import com.example.climate_backend.domain.user.service.MyPageService;
import com.example.climate_backend.domain.verification.dto.request.VerificationRequestDto;
import com.example.climate_backend.domain.verification.dto.request.VerificationUpdateRequestDto;
import com.example.climate_backend.domain.verification.dto.response.VerificationResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/mypage")
@Tag(name = "MyPage API", description = "마이페이지")
public class MyPageController {

    private final MyPageService myPageService;

    @Operation(summary = "마이페이지 조회")
    @GetMapping
    public ResponseEntity<MyPageResDto> getMyPage(@RequestParam String userId) {
        return ResponseEntity.ok(myPageService.getMyPage(userId));
    }

    @Operation(summary = "회원정보 수정")
    @PutMapping
    public ResponseEntity<MyPageResDto> updateMyPage(@RequestParam String userId, @RequestBody MyPageReqDto myPageReqDto) {
        return ResponseEntity.ok(myPageService.updateMyPage(userId, myPageReqDto));
    }

    @Operation(summary = "프로필 사진 수정")
    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateProfileImage(@RequestParam String userId, @RequestPart(required = false, value = "profile") MultipartFile file) {
        myPageService.updateProfileImage(userId, file);
        return ResponseEntity.ok("프로필 사진이 수정되었습니다.");
    }

    @Operation(summary = "커뮤니티 작성 기록 조회")
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> getMyPosts(@RequestParam String userId) {
        return ResponseEntity.ok(myPageService.getMyPosts(userId));
    }

    @Operation(summary = "멍로깅 기록 조회")
    @GetMapping("/logs")
    public ResponseEntity<List<VerificationResponseDto>> getMyLogs(@RequestParam String userId) {
        return ResponseEntity.ok(myPageService.getMyLogs(userId));
    }

    @Operation(summary = "멍로깅 기록 수정")
    @PutMapping("/logs")
    public ResponseEntity<VerificationResponseDto> updateMyLogs(@RequestParam String userId, @RequestParam Long verificationId,
        @RequestBody VerificationUpdateRequestDto verificationUpdateRequestDto) {
        return ResponseEntity.ok(myPageService.updateMyLogs(userId, verificationId, verificationUpdateRequestDto));
    }

    @Operation(summary = "멍로깅 기록 삭제")
    @DeleteMapping("/logs")
    public ResponseEntity<String> deleteMyLogs(@RequestParam String userId, @RequestParam Long verificationId) {
        myPageService.deleteMyLogs(userId, verificationId);
        return ResponseEntity.ok("멍로깅 기록이 삭제되었습니다.");
    }

}
