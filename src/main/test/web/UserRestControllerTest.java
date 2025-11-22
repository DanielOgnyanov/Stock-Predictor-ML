package web;

import com.daniel.stockpredictorml.models.dto.ChangeOldPasswordDTO;
import com.daniel.stockpredictorml.models.dto.LoginResponseDTO;
import com.daniel.stockpredictorml.models.dto.UserRegistrationDTO;
import com.daniel.stockpredictorml.service.UserService;
import com.daniel.stockpredictorml.repository.UserRepository;

import com.daniel.stockpredictorml.web.UserRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = com.daniel.stockpredictorml.StockPredictorML.class)
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private Authentication authentication;


    @Test
    void registerUser_ShouldReturnCreated() throws Exception {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setEmail("test@mail.com");
        dto.setPassword("Password123!");

        when(userService.registerUser(any())).thenReturn(null);

        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "test@mail.com",
                                  "password": "Password123!"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User registered successfully"));
    }


    @Test
    void login_ShouldReturnToken() throws Exception {
        LoginResponseDTO response = new LoginResponseDTO("token123", "user@mail.com", "USER");
        when(userService.login(any())).thenReturn(response);

        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "user@mail.com",
                                  "password": "pass"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token123"));
    }


}
