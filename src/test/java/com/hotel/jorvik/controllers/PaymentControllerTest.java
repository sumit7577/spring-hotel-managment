package com.hotel.jorvik.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.jorvik.models.*;
import com.hotel.jorvik.models.DTO.payment.CreatePayment;
import com.hotel.jorvik.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.config.name=application-test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EntertainmentTypeRepository entertainmentTypeRepository;
    @Autowired
    private EntertainmentRepository entertainmentRepository;
    @Autowired
    private EntertainmentReservationRepository entertainmentReservationRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomReservationRepository roomReservationRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        Role role = new Role(Role.ERole.ROLE_USER);
        roleRepository.save(role);
        User user = new User("test", "test", "test@example.com", "123456789", 0, "test", role);
        userRepository.save(user);
    }

    private void setupTestRoom() {
        RoomType roomType = new RoomType(4, 100, 100);
        roomTypeRepository.save(roomType);
        Room room1 = new Room(1, 1234, roomType);
        Room room2 = new Room(2, 1235, roomType);
        roomRepository.saveAll(List.of(room1, room2));
    }

    private void setupTestEntertainment() {
        EntertainmentType entertainmentType1 = new EntertainmentType("Tennis", 100);
        EntertainmentType entertainmentType2 = new EntertainmentType("Kayak", 200);
        entertainmentTypeRepository.saveAll(List.of(entertainmentType1, entertainmentType2));
        Entertainment entertainment1 = new Entertainment("Tennis1", 122, entertainmentType1);
        Entertainment entertainment2 = new Entertainment("Tennis2", 122, entertainmentType1);
        Entertainment entertainment3 = new Entertainment("Tennis3", 122, entertainmentType1);
        entertainmentRepository.saveAll(List.of(entertainment1, entertainment2, entertainment3));
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = "USER")
    public void testCreatePaymentIntent_Room() throws Exception {
        setupTestRoom();
        CreatePayment createPayment = new CreatePayment("Room", "2028-10-12", "2028-10-13", 1);

        mockMvc.perform(post("/api/v1/payment/create-payment-intent")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createPayment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount", is(100)))
                .andExpect(jsonPath("$.reservationId", is(1)));
        assertEquals(1, roomReservationRepository.findAll().size());
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = "USER")
    public void testCreatePaymentIntent_Room_WrongDate() throws Exception {
        setupTestRoom();
        CreatePayment createPayment = new CreatePayment("Room", "2021-10-12", "2021-10-13", 1);

        mockMvc.perform(post("/api/v1/payment/create-payment-intent")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createPayment)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = "USER")
    public void testCreatePaymentIntent_Entertainment() throws Exception {
        setupTestEntertainment();
        CreatePayment createPayment = new CreatePayment("Tennis", "2028-10-12", "2028-10-13", "12-00", "14-00", 1);

        mockMvc.perform(post("/api/v1/payment/create-payment-intent")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createPayment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount", is(2600)))
                .andExpect(jsonPath("$.reservationId", is(1)));
        assertEquals(1, entertainmentReservationRepository.findAll().size());
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = "USER")
    public void testCreatePaymentIntent_Entertainment_WrongTime() throws Exception {
        setupTestEntertainment();
        CreatePayment createPayment = new CreatePayment("Tennis", "2021-10-12", "2021-10-13", "12-00", "14-00", 1);

        mockMvc.perform(post("/api/v1/payment/create-payment-intent")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createPayment)))
                .andExpect(status().is4xxClientError());
    }
}
