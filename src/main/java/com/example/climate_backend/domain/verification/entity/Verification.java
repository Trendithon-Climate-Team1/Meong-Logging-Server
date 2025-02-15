package com.example.climate_backend.domain.verification.entity;

import com.example.climate_backend.domain.user.entity.User;
import com.example.climate_backend.domain.verification.dto.request.VerificationRequestDto;
import com.example.climate_backend.domain.verification.enums.VerificationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Verification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String courseName;

    @Column(nullable = false)
    private String date;

    @ElementCollection
    @CollectionTable(name = "verification_path", joinColumns = @JoinColumn(name = "verification_id"))
    private List<Coordinate> path;

    @Setter
    private String uploadedImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationStatus status;


    public static Verification createFromDto(VerificationRequestDto dto,String imageUrl, User user) {
        return Verification.builder()
                .user(user)
                .courseName(dto.getCourseName())
                .date(dto.getDate())
                .path(dto.getPath())
                .uploadedImage(imageUrl)
                .status(VerificationStatus.PENDING)
                .build();
    }

    // 인증 승인
    public void approve() {
        this.status = VerificationStatus.APPROVED;
    }

    // 인증 거절
    public void reject() {
        this.status = VerificationStatus.REJECTED;
    }
}