package com.daniel.stockpredictorml.service.Impl;
import com.daniel.stockpredictorml.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendPasswordResetEmail(String to, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Password Reset Request");
        message.setText("Hello,\n\n" +
                "We received a request to reset your password. " +
                "Click the link below to set a new password:\n\n" +
                resetLink + "\n\n" +
                "This link will expire in 30 minutes.\n\n" +
                "If you didn’t request a password reset, please ignore this message.\n\n" +
                "Best regards,\nStock Predictor ML Team");

        mailSender.send(message);
    }
}
