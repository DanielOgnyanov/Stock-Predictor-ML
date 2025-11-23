package service;

import com.daniel.stockpredictorml.config.JwtUtil;
import com.daniel.stockpredictorml.exceptions.EmailAlreadyExistsException;
import com.daniel.stockpredictorml.exceptions.InvalidCredentialsException;
import com.daniel.stockpredictorml.exceptions.UserNotFoundException;
import com.daniel.stockpredictorml.models.dto.ChangeOldPasswordDTO;
import com.daniel.stockpredictorml.models.dto.LoginRequestDTO;
import com.daniel.stockpredictorml.models.dto.UserRegistrationDTO;
import com.daniel.stockpredictorml.models.entities.UserEntity;
import com.daniel.stockpredictorml.models.enums.Role;
import com.daniel.stockpredictorml.repository.UserRepository;
import com.daniel.stockpredictorml.service.Impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void createInitialUser_shouldCreateUserIfNotExists() {
        when(userRepository.existsByEmail("test.user@example.com")).thenReturn(false);
        when(passwordEncoder.encode("TestPassword123")).thenReturn("encodedPass");

        userService.createInitialUser();

        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void createInitialUser_shouldNotCreateIfExists() {
        when(userRepository.existsByEmail("test.user@example.com")).thenReturn(true);

        userService.createInitialUser();

        verify(userRepository, never()).save(any());
    }


    @Test
    void registerUser_shouldSaveUser() {
        UserRegistrationDTO dto = new UserRegistrationDTO("John", "Doe", "john@mail.com", "pass123");

        when(userRepository.existsByEmail("john@mail.com")).thenReturn(false);
        when(passwordEncoder.encode("pass123")).thenReturn("encoded");
        when(userRepository.save(any(UserEntity.class))).thenAnswer(i -> i.getArgument(0));

        UserEntity user = userService.registerUser(dto);

        assertEquals("john@mail.com", user.getEmail());
        assertEquals(Role.USER, user.getRole());
    }

    @Test
    void registerUser_shouldThrowWhenEmailExists() {
        UserRegistrationDTO dto = new UserRegistrationDTO("John", "Doe", "john@mail.com", "pass123");

        when(userRepository.existsByEmail("john@mail.com")).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> userService.registerUser(dto));
    }


    @Test
    void login_shouldReturnToken() {
        LoginRequestDTO request = new LoginRequestDTO("user@mail.com", "123456");

        UserEntity user = UserEntity.builder()
                .email("user@mail.com")
                .password("encodedpass")
                .lastName("Doe")
                .role(Role.USER)
                .build();

        when(userRepository.findByEmail("user@mail.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123456", "encodedpass")).thenReturn(true);
        when(jwtUtil.generateToken("user@mail.com")).thenReturn("TOKEN123");

        var response = userService.login(request);

        assertEquals("TOKEN123", response.getToken());
        assertEquals("Doe", response.getUsername());
        assertEquals("USER", response.getUserRole());

    }

    @Test
    void login_shouldThrowForInvalidEmail() {
        LoginRequestDTO request = new LoginRequestDTO("wrong@mail.com", "123");

        when(userRepository.findByEmail("wrong@mail.com")).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class, () -> userService.login(request));
    }

    @Test
    void login_shouldThrowForInvalidPassword() {
        LoginRequestDTO request = new LoginRequestDTO("user@mail.com", "wrong");

        UserEntity user = UserEntity.builder()
                .email("user@mail.com")
                .password("encodedpass")
                .build();

        when(userRepository.findByEmail("user@mail.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "encodedpass")).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> userService.login(request));
    }


    @Test
    void changeOldPassword_shouldChangePassword() {
        UserEntity user = UserEntity.builder()
                .email("user@mail.com")
                .password("oldEncoded")
                .build();

        ChangeOldPasswordDTO dto = new ChangeOldPasswordDTO("oldPass", "newPass");

        when(userRepository.findByEmail("user@mail.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("oldPass", "oldEncoded")).thenReturn(true);
        when(passwordEncoder.encode("newPass")).thenReturn("newEncoded");

        userService.changeOldPassword("user@mail.com", dto);

        verify(userRepository).save(user);
        assertEquals("newEncoded", user.getPassword());
    }

    @Test
    void changeOldPassword_shouldThrowIfUserNotFound() {
        ChangeOldPasswordDTO dto = new ChangeOldPasswordDTO("old", "new");

        when(userRepository.findByEmail("user@mail.com")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.changeOldPassword("user@mail.com", dto));
    }

    @Test
    void changeOldPassword_shouldThrowIfOldPasswordWrong() {
        UserEntity user = UserEntity.builder()
                .email("user@mail.com")
                .password("oldEncoded")
                .build();

        ChangeOldPasswordDTO dto = new ChangeOldPasswordDTO("wrongOld", "new");

        when(userRepository.findByEmail("user@mail.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongOld", "oldEncoded")).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> userService.changeOldPassword("user@mail.com", dto));
    }
}
