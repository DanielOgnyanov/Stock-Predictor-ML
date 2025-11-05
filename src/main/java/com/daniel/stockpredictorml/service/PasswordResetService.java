package com.daniel.stockpredictorml.service;

import com.daniel.stockpredictorml.models.entities.PasswordResetTokenEntity;
import com.daniel.stockpredictorml.models.entities.UserEntity;

import java.util.Optional;

public interface PasswordResetService {


    Optional<PasswordResetTokenEntity> createPasswordResetToken(String email);
    Optional<UserEntity> validatePasswordResetToken(String token);
    void markTokenAsUsed(String token);
}
