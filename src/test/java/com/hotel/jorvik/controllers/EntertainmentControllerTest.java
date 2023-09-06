package com.hotel.jorvik.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.jorvik.models.*;
import com.hotel.jorvik.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.config.name=application-test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EntertainmentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EntertainmentTypeRepository entertainmentTypeRepository;
    @Autowired
    private EntertainmentRepository entertainmentRepository;
    @Autowired
    private EntertainmentReservationRepository entertainmentReservationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void setup() {
        EntertainmentType entertainmentType = new EntertainmentType("Tennis", 100);
        entertainmentTypeRepository.save(entertainmentType);
        Entertainment entertainment1 = new Entertainment("Tennis1", 122, entertainmentType);
        Entertainment entertainment2 = new Entertainment("Tennis2", 122, entertainmentType);
        Entertainment entertainment3 = new Entertainment("Tennis3", 122, entertainmentType);
        entertainmentRepository.saveAll(List.of(entertainment1, entertainment2, entertainment3));
        Role role = new Role(Role.ERole.ROLE_USER);
        roleRepository.save(role);
        User user = new User("test", "test", "test@example.com", "123456789", 0, "test", role);
        userRepository.save(user);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2028-10-10 07:00", formatter);
        Timestamp timestampFrom = Timestamp.valueOf(dateTime);
        dateTime = LocalDateTime.parse("2028-10-10 13:00", formatter);
        Timestamp timestampTo = Timestamp.valueOf(dateTime);
        EntertainmentReservation entertainmentReservation = new EntertainmentReservation(timestampFrom, timestampTo, new Timestamp(System.currentTimeMillis()), user, entertainment1);
        entertainmentReservationRepository.save(entertainmentReservation);
    }

    @Test
    public void getTypes() throws Exception {
        mockMvc.perform(get("/api/v1/entertainment/getTypes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", is(1)));
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = "USER")
    public void getAvailableElementsOccupied() throws Exception {
        mockMvc.perform(get("/api/v1/entertainment/getElements/Tennis/2028-10-10/08-00/2028-10-10/12-00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", is(2)));
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = "USER")
    public void getAvailableElementsFree() throws Exception {
        mockMvc.perform(get("/api/v1/entertainment/getElements/Tennis/2028-11-10/08-00/2028-11-10/12-00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", is(3)));
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = "ADMIN")
    public void updatePrice() throws Exception {
        String json = "[" +
                "{" +
                "\"id\": 1, " +
                "\"name\": \"Tennis\", " +
                "\"price\": 50" +
                "}" +
                "]";
        mockMvc.perform(patch("/api/v1/entertainment/update-price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
        assertEquals(50, entertainmentTypeRepository.findAll().get(0).getPrice());
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = "USER")
    public void unauthorizedUpdatePrice() throws Exception {
        String json = "[" +
                "{" +
                "\"id\": 1, " +
                "\"name\": \"Tennis\", " +
                "\"price\": 50" +
                "}" +
                "]";
        mockMvc.perform(patch("/api/v1/entertainment/update-price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized());
        assertEquals(100, entertainmentTypeRepository.findAll().get(0).getPrice());
    }
}