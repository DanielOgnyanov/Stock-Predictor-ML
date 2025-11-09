package com.daniel.stockpredictorml.service;



import com.daniel.stockpredictorml.models.dto.ChangeOldPasswordDTO;
import com.daniel.stockpredictorml.models.dto.LoginRequestDTO;
import com.daniel.stockpredictorml.models.dto.LoginResponseDTO;
import com.daniel.stockpredictorml.models.dto.UserRegistrationDTO;
import com.daniel.stockpredictorml.models.entities.UserEntity;


public interface UserService {

    void createInitialUser();
    UserEntity registerUser(UserRegistrationDTO userRegistrationDTO);
    LoginResponseDTO login(LoginRequestDTO request);
    void changeOldPassword(String email, ChangeOldPasswordDTO changeOldPasswordDTO);
}
