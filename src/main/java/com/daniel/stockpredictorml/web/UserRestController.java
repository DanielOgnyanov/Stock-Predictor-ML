package com.daniel.stockpredictorml.web;

import com.daniel.stockpredictorml.models.dto.ChangeOldPasswordDTO;
import com.daniel.stockpredictorml.models.dto.LoginRequestDTO;
import com.daniel.stockpredictorml.models.dto.LoginResponseDTO;
import com.daniel.stockpredictorml.models.dto.UserRegistrationDTO;
import com.daniel.stockpredictorml.models.entities.UserEntity;
import com.daniel.stockpredictorml.repository.UserRepository;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private final com.daniel.stockpredictorml.service.UserService userService;
    private final UserRepository userRepository;

    public UserRestController(com.daniel.stockpredictorml.service.UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDTO registrationDto) {
            log.info("Registering new user with email: {}", registrationDto.getEmail());
            UserEntity savedUser = userService.registerUser(registrationDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "User registered successfully"));


    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        log.info("Login attempt for user: {}", loginRequest.getEmail());
        LoginResponseDTO response = userService.login(loginRequest);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/change-password")
    public ResponseEntity<?> changeOldPassword(@RequestBody @Valid ChangeOldPasswordDTO request,
                                               Authentication authentication) {
        final String email = authentication.getName();

        log.info("Password change request received for user: {}", email);

        try {
            userService.changeOldPassword(email, request);
            log.info("Password successfully changed for user: {}", email);
            return ResponseEntity.ok(Map.of("message", "Password changed successfully"));
        } catch (IllegalArgumentException ex) {
            log.warn("Password change failed for user {}: {}", email, ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            log.error("Unexpected error while changing password for user {}: {}", email, ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred. Please try again later."));
        }
    }

    
}
