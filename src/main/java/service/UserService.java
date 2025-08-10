package main.java.service;


import main.java.models.dto.UserRegistrationDTO;
import main.java.models.entities.UserEntity;

public interface UserService {

    UserEntity registerUser(UserRegistrationDTO userRegistrationDTO);
}
