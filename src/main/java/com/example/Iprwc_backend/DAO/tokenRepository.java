package com.example.Iprwc_backend.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.cdi.JpaRepositoryExtension;

import com.example.Iprwc_backend.Model.PasswordResetToken;
import com.example.Iprwc_backend.Model.User;

public interface tokenRepository extends JpaRepository<PasswordResetToken, Long> {
    // findByToken
    PasswordResetToken findByToken(String token);
    
}
