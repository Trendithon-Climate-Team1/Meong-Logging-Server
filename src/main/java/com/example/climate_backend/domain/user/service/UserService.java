package com.example.climate_backend.domain.user.service;

import com.example.climate_backend.domain.user.dto.request.LoginDto;
import com.example.climate_backend.domain.user.dto.request.SignupDto;
import com.example.climate_backend.domain.user.dto.response.UserInfoDto;
import com.example.climate_backend.domain.user.entity.User;
import com.example.climate_backend.domain.user.repository.UserRepository;
import com.example.climate_backend.domain.user.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordUtil passwordUtil;

    public void signup(SignupDto signupDto){
        validateIsDuplicatedUserId(signupDto.getUserId());
        validateIsDuplicatedEmail(signupDto.getEmail());

        User user = User.createUser(signupDto, passwordUtil.hashPassword(signupDto.getPassword()));
        userRepository.save(user);
    }

    public UserInfoDto login(LoginDto loginDto) {
        User user = findExistingUserByUserId(loginDto.getUserId());
        if(passwordUtil.matchPassword(loginDto.getPassword(), user.getPassword()))
            return UserInfoDto.builder()
                    .userId(user.getUserId())
                    .nickname(user.getNickname())
                    .build();
        else
            throw new RuntimeException("올바르지 않은 비밀번호입니다.");
    }

    private User findExistingUserByUserId(String userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        if(user.isPresent())
            return user.get();
        else
            throw new RuntimeException("등록되지 않은 아이디입니다.");
    }

    public void validateIsDuplicatedUserId(String userId) {
        if (userRepository.existsByUserId(userId)) {
            throw new RuntimeException("이미 사용 중인 아이디입니다.");
        }
    }

    public void validateIsDuplicatedEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("이미 사용 중인 이메일입니다.");
        }
    }

}
