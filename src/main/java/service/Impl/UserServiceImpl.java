package service.Impl;

import exceptions.ResourceConflictException;
import models.dto.UserRegistrationDTO;
import models.entities.UserEntity;
import models.enums.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repository.UserRepository;
import service.UserService;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserEntity registerUser(UserRegistrationDTO userRegistrationDTO) {


        if (userRepository.existsByEmail(userRegistrationDTO.getEmail())) {
            throw new ResourceConflictException("Email already registered: " + userRegistrationDTO.getEmail());
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
}
