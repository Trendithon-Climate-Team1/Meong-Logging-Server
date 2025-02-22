package com.example.climate_backend.domain.user.entity;

import com.example.climate_backend.domain.post.entity.Post;
import com.example.climate_backend.domain.user.dto.request.MyPageReqDto;
import com.example.climate_backend.domain.user.dto.request.SignupDto;
import com.example.climate_backend.domain.user.dto.response.MyPageResDto;
import com.example.climate_backend.domain.user.enums.Role;
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
    private int point;
    private String profileImg;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Verification> verifications = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;

    public static User createUser(SignupDto signupDto, String hashPassword){
        return User.builder()
                .userId(signupDto.getUserId())
                .password(hashPassword)
                .email(signupDto.getEmail())
                .nickname(signupDto.getNickname())
                .petName(signupDto.getPetName())
                .role(Role.ROLE_USER)
                .point(0)
                .build();
    }

    public void addVerification(Verification verification) {
        this.verifications.add(verification);
        verification.setUser(this); 
    }

    public void update(MyPageReqDto myPageReqDto) {
        this.userId = myPageReqDto.getUserId();
        this.password = myPageReqDto.getPassword();
        this.email = myPageReqDto.getEmail();
        this.nickname = myPageReqDto.getNickname();
        this.petName = myPageReqDto.getPetName();
    }

    public void updateProfileImage(String profileImg) {
        this.profileImg = profileImg;
    }
}
