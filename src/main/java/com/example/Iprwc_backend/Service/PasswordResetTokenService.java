package com.example.Iprwc_backend.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Iprwc_backend.DAO.tokenRepository;
import com.example.Iprwc_backend.Model.PasswordResetToken;
import com.example.Iprwc_backend.Model.User;


@Service
public class PasswordResetTokenService {
    @Autowired
    private tokenRepository tokenRepository;

    // createTokenForUserWithEmail function
@Autowired
    private UserService userService;

    public PasswordResetToken createTokenForUserWithEmail(String email) {
        // Find the user by email
        User user = userService.findByEmail(email);
        if (user != null) {
            // Generate a unique token (e.g., a UUID)
            String tokenValue = UUID.randomUUID().toString();

            // Calculate the expiration date (e.g., 24 hours from now)
            LocalDateTime expiryDate = LocalDateTime.now().plusHours(24);

            // Create the password reset token entity
            PasswordResetToken token = new PasswordResetToken();
            token.setToken(tokenValue);
            token.setExpiryDate(expiryDate);
            token.setUser(user);

            // Save the token in the database
            return tokenRepository.save(token);
        } else {
            // User not found
            return null;
        }
    }

    public PasswordResetToken findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public void deleteToken(PasswordResetToken token) {
        tokenRepository.delete(token);
    }

    // Other methods for finding and deleting tokens
}




