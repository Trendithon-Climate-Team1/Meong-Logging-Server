package com.example.climate_backend.domain.verification.repository;

import com.example.climate_backend.domain.user.entity.User;
import com.example.climate_backend.domain.verification.entity.Verification;
import com.example.climate_backend.domain.verification.enums.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, Long> {
    List<Verification> findByUser(User user);
    List<Verification> findByStatus(VerificationStatus status);

}

