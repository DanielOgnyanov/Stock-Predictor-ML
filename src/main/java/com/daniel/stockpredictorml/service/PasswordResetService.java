package com.daniel.stockpredictorml.service;

import com.daniel.stockpredictorml.models.entities.PasswordResetTokenEntity;
import com.daniel.stockpredictorml.models.entities.UserEntity;

import java.util.Optional;

public interface PasswordResetService {

    Optional<String> createPasswordResetToken(String email);
    boolean resetPassword(String token, String newPassword);
}
