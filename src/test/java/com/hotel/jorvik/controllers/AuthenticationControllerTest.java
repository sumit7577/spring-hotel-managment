package com.hotel.jorvik.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.jorvik.models.DTO.auth.AuthenticationRequest;
import com.hotel.jorvik.models.DTO.auth.RegisterRequest;
import com.hotel.jorvik.models.DTO.user.PasswordResetConfirmedRequest;
import com.hotel.jorvik.models.DTO.user.PasswordResetRequest;
import com.hotel.jorvik.models.Role;
import com.hotel.jorvik.models.TokenType;
import com.hotel.jorvik.models.User;
import com.hotel.jorvik.repositories.RoleRepository;
import com.hotel.jorvik.repositories.TokenTypeRepository;
import com.hotel.jorvik.repositories.UserRepository;
import com.hotel.jorvik.security.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.config.name=application-test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenTypeRepository tokenTypeRepository;
    @MockBean
    private EmailService emailService;
    private final String password = "test_p@ssworD13";

    @BeforeEach
    public void setup() {
        Role role = new Role(Role.ERole.ROLE_USER);
        roleRepository.save(role);
        User user = new User("test", "test", "test@example.com", "123456789", 0, passwordEncoder.encode(password), role);
        userRepository.save(user);
        TokenType tokenType1 = new TokenType(TokenType.ETokenType.ACCESS);
        TokenType tokenType2 = new TokenType(TokenType.ETokenType.RESET_PASSWORD);
        TokenType tokenType3 = new TokenType(TokenType.ETokenType.EMAIL_CONFIRMATION);
        tokenTypeRepository.save(tokenType1);
        tokenTypeRepository.save(tokenType2);
        tokenTypeRepository.save(tokenType3);
        Mockito.doNothing().when(emailService).sendConfirmationEmail(any(User.class));
    }

    @Test
    public void register() throws Exception {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .email("test2@example.com")
                .firstName("test")
                .lastName("test")
                .phoneNumber("987654321")
                .password(password)
                .build();
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.token").isNotEmpty());
    }

    @Test
    public void registerWithExistingNumber() throws Exception {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .email("test2@example.com")
                .firstName("test")
                .lastName("test")
                .phoneNumber("123456789")
                .password(password)
                .build();
        MvcResult result = mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        assertEquals("{\"status\":\"fail\",\"data\":\"Phone already exists\"}", responseJson);
    }

    @Test
    public void registerWithExistingEmail() throws Exception {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .email("test@example.com")
                .firstName("test")
                .lastName("test")
                .phoneNumber("987654321")
                .password(password)
                .build();
        MvcResult result = mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        assertEquals("{\"status\":\"fail\",\"data\":\"Email already exists\"}", responseJson);
    }

    @Test
    public void registerWithInvalidEmail() throws Exception {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .email("test.com")
                .firstName("test")
                .lastName("test")
                .phoneNumber("987654321")
                .password(password)
                .build();
        MvcResult result = mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        assertEquals("{\"status\":\"fail\",\"data\":[\"Email must be a valid email address\"]}", responseJson);
    }

    @Test
    public void registerWithInvalidPassword() throws Exception {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .email("test2@example.com")
                .firstName("test")
                .lastName("test")
                .phoneNumber("987654321")
                .password("1234")
                .build();
        MvcResult result = mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        assertEquals("{\"status\":\"fail\",\"data\":\"Password is not valid\"}", responseJson);
    }

    @Test
    public void authenticate() throws Exception {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email("test@example.com")
                .password(password)
                .build();

        mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").isNotEmpty());
    }

    @Test
    public void authenticateWithInvalidEmail() throws Exception {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email("test2example.com")
                .password(password)
                .build();
        MvcResult result = mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        assertEquals("{\"status\":\"fail\",\"data\":\"Invalid email or password\"}", responseJson);
    }

    @Test
    public void authenticateWithInvalidPassword() throws Exception {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email("test@example.com")
                .password("1234")
                .build();
        MvcResult result = mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        assertEquals("{\"status\":\"fail\",\"data\":\"Invalid email or password\"}", responseJson);
    }

    @Test
    public void authenticateNonExistingUser() throws Exception {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email("test3@example.com")
                .password(password)
                .build();
        MvcResult result = mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        assertEquals("{\"status\":\"fail\",\"data\":\"Invalid email or password\"}", responseJson);
    }

    @Test
    public void confirmEmail() throws Exception {
        String testToken = "test_token";
        mockMvc.perform(get("/api/v1/auth/email-confirmation/" + testToken))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void passwordReset() throws Exception {
        PasswordResetRequest passwordResetRequest = PasswordResetRequest.builder()
                .email("test@example.com")
                .build();
        mockMvc.perform(post("/api/v1/auth/password-reset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordResetRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void passwordResetConfirmNotValidPass() throws Exception {
        String testToken = "test_token";
        PasswordResetConfirmedRequest passwordResetConfirmedRequest = PasswordResetConfirmedRequest.builder()
                .password("1234")
                .build();
        MvcResult result = mockMvc.perform(post("/api/v1/auth/password-reset-confirm/" + testToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordResetConfirmedRequest)))
                .andExpect(status().is4xxClientError())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        assertEquals("{\"status\":\"fail\",\"data\":\"Password is not valid\"}", responseJson);
    }
}
