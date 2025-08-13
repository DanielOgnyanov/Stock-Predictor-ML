package main.java.service.Impl;



import main.java.config.JwtUtil;
import main.java.exceptions.EmailAlreadyExistsException;
import main.java.exceptions.InvalidCredentialsException;
import main.java.models.dto.LoginRequestDTO;
import main.java.models.dto.LoginResponseDTO;
import main.java.models.dto.UserRegistrationDTO;
import main.java.models.entities.UserEntity;
import main.java.models.enums.Role;
import main.java.repository.UserRepository;
import main.java.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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


}
