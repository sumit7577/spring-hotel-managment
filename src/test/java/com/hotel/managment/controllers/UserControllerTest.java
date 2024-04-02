package com.hotel.managment.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.managment.models.dto.user.DiscountChangeRequest;
import com.hotel.managment.models.dto.user.UserUpdateRequest;
import com.hotel.managment.models.Role;
import com.hotel.managment.models.User;
import com.hotel.managment.repositories.RoleRepository;
import com.hotel.managment.repositories.UserRepository;
import com.hotel.managment.security.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest(properties = "spring.config.name=application-test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private UserRepository userRepository;
  @Autowired private RoleRepository roleRepository;

  @MockBean private EmailService emailService;

  @BeforeEach
  public void setup() {
    Role role = new Role(Role.RoleEnum.ROLE_USER);
    roleRepository.save(role);
    User user = new User("test", "test", "test@example.com", "123456789", 0, "test", role);
    userRepository.save(user);

    Mockito.doNothing().when(emailService).sendConfirmationEmail(any(User.class));

    Authentication auth = new UsernamePasswordAuthenticationToken("test@example.com", "password");
    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
    securityContext.setAuthentication(auth);
    SecurityContextHolder.setContext(securityContext);
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "ADMIN")
  public void getByToken() throws Exception {
    mockMvc
        .perform(get("/api/v1/user/get-by-matching/test"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data[0].id", is(1)))
        .andExpect(jsonPath("$.data[0].firstName", is("test")));
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "USER")
  public void unauthorizedGetByToken() throws Exception {
    mockMvc.perform(get("/api/v1/user/get-by-matching/test")).andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "ADMIN")
  public void getById() throws Exception {
    mockMvc
        .perform(get("/api/v1/user/get/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.id", is(1)))
        .andExpect(jsonPath("$.data.firstName", is("test")));
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "ADMIN")
  public void getAll() throws Exception {
    mockMvc
        .perform(get("/api/v1/user/get-all"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.length()", is(1)))
        .andExpect(jsonPath("$.data[0].firstName", is("test")));
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "ADMIN")
  public void checkEmailVerification() throws Exception {
    mockMvc
        .perform(get("/api/v1/user/check-email-verification"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data", is(false)));
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "ADMIN")
  public void update() throws Exception {
    UserUpdateRequest userUpdateRequest =
        UserUpdateRequest.builder()
            .firstName("test2")
            .lastName("test2")
            .email("test2@example.com")
            .phone("987654321")
            .discount(10)
            .build();
    mockMvc
        .perform(
            put("/api/v1/user/update/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userUpdateRequest)))
        .andExpect(status().isOk());
    User user = userRepository.findById(1).get();
    assertEquals(10, user.getDiscount());
    assertEquals("test2", user.getFirstName());
    assertEquals("test2", user.getLastName());
    assertEquals("987654321", user.getPhone());
    assertEquals("test2@example.com", user.getEmail());
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "ADMIN")
  public void updateDiscount() throws Exception {
    DiscountChangeRequest discountChangeRequest =
        DiscountChangeRequest.builder().discount(25).build();
    mockMvc
        .perform(
            put("/api/v1/user/update-discount/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(discountChangeRequest)))
        .andExpect(status().isOk());
    User user = userRepository.findById(1).get();
    assertEquals(25, user.getDiscount());
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "USERN")
  public void unauthorizedUpdateDiscount() throws Exception {
    DiscountChangeRequest discountChangeRequest =
        DiscountChangeRequest.builder().discount(25).build();
    mockMvc
        .perform(
            put("/api/v1/user/update-discount/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(discountChangeRequest)))
        .andExpect(status().isUnauthorized());
  }
}
