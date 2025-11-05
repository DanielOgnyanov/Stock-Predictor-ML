package com.daniel.stockpredictorml.web;

import com.daniel.stockpredictorml.service.PasswordResetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/request-password-reset")
    public ResponseEntity<?> requestPasswordReset(@RequestParam String email) {
        var tokenOpt = passwordResetService.createPasswordResetToken(email);
        if (tokenOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("No user found with that email.");
        }
        return ResponseEntity.ok("Password reset email sent successfully.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token,
                                           @RequestParam String newPassword) {
        boolean success = passwordResetService.resetPassword(token, newPassword);
        if (!success) {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }
        return ResponseEntity.ok("Password has been reset successfully.");
    }
}
