package com.daniel.stockpredictorml.web;

import com.daniel.stockpredictorml.models.dto.LoginRequestDTO;
import com.daniel.stockpredictorml.models.dto.LoginResponseDTO;
import com.daniel.stockpredictorml.models.dto.UserRegistrationDTO;
import com.daniel.stockpredictorml.models.entities.UserEntity;
import com.daniel.stockpredictorml.repository.UserRepository;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

            UserEntity savedUser = userService.registerUser(registrationDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");


    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        LoginResponseDTO response = userService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    
}
