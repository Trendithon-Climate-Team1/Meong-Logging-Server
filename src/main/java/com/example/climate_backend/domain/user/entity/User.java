package com.example.climate_backend.domain.user.entity;

import com.example.climate_backend.domain.user.dto.request.SignupDto;
import com.example.climate_backend.domain.verification.entity.Verification;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String nickname;
    private String petName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Verification> verifications = new ArrayList<>();


    public static User createUser(SignupDto signupDto, String hashPassword){
        return User.builder()
                .userId(signupDto.getUserId())
                .password(hashPassword)
                .email(signupDto.getEmail())
                .nickname(signupDto.getNickname())
                .petName(signupDto.getPetName())
                .build();
    }

    public void addVerification(Verification verification) {
        this.verifications.add(verification);
        verification.setUser(this); // ✅ 관계 설정
    }
}
