package com.daniel.stockpredictorml.service;

public interface EmailService {
    void sendPasswordResetEmail(String to, String resetLink);
}
