package com.daniel.stockpredictorml.web;

import com.daniel.stockpredictorml.models.dto.PasswordResetRequestDTO;
import com.daniel.stockpredictorml.models.dto.PasswordUpdateRequestDTO;
import com.daniel.stockpredictorml.service.PasswordResetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/request-password-reset")
    public ResponseEntity<?> requestPasswordReset(@RequestBody PasswordResetRequestDTO request) {
        var tokenOpt = passwordResetService.createPasswordResetToken(request.getEmail());
        if (tokenOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("No user found with that email.");
        }
        return ResponseEntity.ok("Password reset email sent successfully.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordUpdateRequestDTO request) {
        boolean success = passwordResetService.resetPassword(request.getToken(), request.getNewPassword());
        if (!success) {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }
        return ResponseEntity.ok("Password has been reset successfully.");
    }
}
