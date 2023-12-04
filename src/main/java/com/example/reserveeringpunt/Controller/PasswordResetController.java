package com.example.reserveeringpunt.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import com.example.reserveeringpunt.Model.PasswordResetToken;
import com.example.reserveeringpunt.Model.User;
import com.example.reserveeringpunt.Service.PasswordResetTokenService;
import com.example.reserveeringpunt.Service.UserService;

@RestController
@RequestMapping("/api/password-reset")
public class PasswordResetController {
    @Autowired
    private PasswordResetTokenService tokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailSender javaMailSender; // Inject JavaMailSender bean
    @Value("${BaseUrlFront}")
    String BaseUrlFront;

    @PostMapping("/request/{email}")
    public ResponseEntity<?> requestPasswordReset(@PathVariable String email) {
        // Find the user by email
        System.out.println(email);
        User user = userService.findByEmail(email);
        if (user != null) {
            // Generate a unique token for the user
            PasswordResetToken token = tokenService.createTokenForUserWithEmail(email);

            if (token != null) {
                // Send a password reset email to the user
                boolean emailSent = sendPasswordResetEmail(user.getEmail(), token.getToken());

                if (emailSent) {
                    return ResponseEntity.ok("Password reset email sent successfully.");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send the email.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate the token.");
            }
        } else {
            return ResponseEntity.badRequest().body("User not found.");
        }
    }

    private boolean sendPasswordResetEmail(String userEmail, String token) {
        try {
            // Create a simple email message
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("backoffice@hypertech.site");
            message.setTo(userEmail);
            message.setSubject("Password Reset");
            message.setText("To reset your password, click the following link:\n\n"
                    + BaseUrlFront + "reset-password?token=" + token);

            javaMailSender.send(message);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        // Verify the token's validity and reset the password
        PasswordResetToken resetToken = tokenService.findByToken(token);
        if (resetToken != null && !resetToken.isExpired()) {
            User user = resetToken.getUser();
            userService.updatePassword(user, newPassword);
            tokenService.deleteToken(resetToken);
            return new ResponseEntity<>("Password reset successfully.", HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid or expired token.");
        }
    }
}
