package com.example.reserveeringpunt.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.cdi.JpaRepositoryExtension;

import com.example.reserveeringpunt.Model.PasswordResetToken;
import com.example.reserveeringpunt.Model.User;

public interface tokenRepository extends JpaRepository<PasswordResetToken, Long> {
    // findByToken
    PasswordResetToken findByToken(String token);

    // deleteByUserId
    void deleteByUserId(Long id);
}
