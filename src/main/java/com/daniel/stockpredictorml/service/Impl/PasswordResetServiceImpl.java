package com.daniel.stockpredictorml.service.Impl;

import com.daniel.stockpredictorml.models.entities.PasswordResetTokenEntity;
import com.daniel.stockpredictorml.models.entities.UserEntity;
import com.daniel.stockpredictorml.repository.PasswordResetTokenRepository;
import com.daniel.stockpredictorml.repository.UserRepository;
import com.daniel.stockpredictorml.service.PasswordResetService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    private final PasswordResetTokenRepository tokenRepository;
    private final UserRepository userRepository;

    public PasswordResetServiceImpl(PasswordResetTokenRepository tokenRepository,
                                    UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Optional<PasswordResetTokenEntity> createPasswordResetToken(String email) {
        Optional<UserEntity> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        UserEntity user = userOpt.get();


        tokenRepository.deleteByUser(user);

        String token = generateSecureToken();
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(30);

        PasswordResetTokenEntity resetToken = new PasswordResetTokenEntity(token, expiryDate, user);
        tokenRepository.save(resetToken);

        return Optional.of(resetToken);
    }

    @Override
    public Optional<UserEntity> validatePasswordResetToken(String token) {
        Optional<PasswordResetTokenEntity> tokenOpt = tokenRepository.findByToken(token);
        if (tokenOpt.isEmpty()) {
            return Optional.empty();
        }

        PasswordResetTokenEntity resetToken = tokenOpt.get();

        if (resetToken.isExpired() || resetToken.isUsed()) {
            return Optional.empty();
        }

        return Optional.of(resetToken.getUser());
    }

    @Override
    @Transactional
    public void markTokenAsUsed(String token) {
        tokenRepository.findByToken(token).ifPresent(t -> {
            t.setUsed(true);
            tokenRepository.save(t);
        });
    }

    private String generateSecureToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
