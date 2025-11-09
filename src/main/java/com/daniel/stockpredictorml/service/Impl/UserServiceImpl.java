package com.daniel.stockpredictorml.service.Impl;

import com.daniel.stockpredictorml.config.JwtUtil;
import com.daniel.stockpredictorml.exceptions.EmailAlreadyExistsException;
import com.daniel.stockpredictorml.exceptions.InvalidCredentialsException;
import com.daniel.stockpredictorml.exceptions.UserNotFoundException;
import com.daniel.stockpredictorml.models.dto.ChangeOldPasswordDTO;
import com.daniel.stockpredictorml.models.dto.LoginRequestDTO;
import com.daniel.stockpredictorml.models.dto.LoginResponseDTO;
import com.daniel.stockpredictorml.models.dto.UserRegistrationDTO;
import com.daniel.stockpredictorml.models.entities.UserEntity;
import com.daniel.stockpredictorml.models.enums.Role;
import com.daniel.stockpredictorml.repository.UserRepository;
import com.daniel.stockpredictorml.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public void createInitialUser() {
        if (!userRepository.existsByEmail("test.user@example.com")) {
            UserEntity initialUser = UserEntity.builder()
                    .firstName("Test")
                    .lastName("User")
                    .email("test.user@example.com")
                    .password(passwordEncoder.encode("TestPassword123"))
                    .role(Role.ADMIN)
                    .enabled(true)
                    .locked(false)
                    .build();

            userRepository.save(initialUser);
        }
    }

    @Override
    public UserEntity registerUser(UserRegistrationDTO userRegistrationDTO) {

        if (userRepository.existsByEmail(userRegistrationDTO.getEmail())) {
            throw new EmailAlreadyExistsException(userRegistrationDTO.getEmail());
        }

        UserEntity user = UserEntity.builder()
                .firstName(userRegistrationDTO.getFirstName())
                .lastName(userRegistrationDTO.getLastName())
                .email(userRegistrationDTO.getEmail())
                .password(passwordEncoder.encode(userRegistrationDTO.getPassword()))
                .role(Role.USER)
                .enabled(true)
                .locked(false)
                .build();


        return userRepository.save(user);

    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        Optional<UserEntity> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty()) {
            throw new InvalidCredentialsException();
        }

        UserEntity user = userOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponseDTO(token, user.getLastName(), user.getRole().toString());
    }

    @Override
    public void changeOldPassword(String email, ChangeOldPasswordDTO changeOldPasswordDTO) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if (!passwordEncoder.matches(changeOldPasswordDTO.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(changeOldPasswordDTO.getNewPassword()));
        userRepository.save(user);
    }


}
