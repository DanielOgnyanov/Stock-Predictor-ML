package service;

import models.dto.UserRegistrationDTO;
import models.entities.UserEntity;

public interface UserService {

    UserEntity registerUser(UserRegistrationDTO userRegistrationDTO);
}
