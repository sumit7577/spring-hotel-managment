package com.hotel.managment.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.managment.models.*;
import com.hotel.managment.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.config.name=application-test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RoomControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private UserRepository userRepository;
  @Autowired private RoleRepository roleRepository;
  @Autowired private RoomTypeRepository roomTypeRepository;
  @Autowired private RoomRepository roomRepository;
  @Autowired private RoomReservationRepository roomReservationRepository;

  @BeforeEach
  public void setup() {
    Role role = new Role(Role.RoleEnum.ROLE_USER);
    roleRepository.save(role);
    User user = new User("test", "test", "test@example.com", "123456789", 0, "test", role);
    userRepository.save(user);
    RoomType roomType = new RoomType(4, 100, 100);
    roomTypeRepository.save(roomType);
    Room room1 = new Room(1, 1234, roomType);
    Room room2 = new Room(2, 1235, roomType);
    Room room3 = new Room(3, 1236, roomType);
    Room room4 = new Room(4, 1237, roomType);
    Room room5 = new Room(5, 1238, roomType);
    roomRepository.saveAll(List.of(room1, room2, room3, room4, room5));
    RoomReservation roomReservation =
        new RoomReservation(
            Date.valueOf("2028-10-10"),
            Date.valueOf("2028-10-12"),
            new Timestamp(System.currentTimeMillis()),
            room1,
            user);
    roomReservationRepository.save(roomReservation);
  }

  @Test
  public void getById() throws Exception {
    mockMvc
        .perform(get("/api/v1/room/get/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.number", is(1)));
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "ADMIN")
  public void add() throws Exception {
    Room room = new Room(5, 1239, roomTypeRepository.findById(1).get());
    mockMvc
        .perform(
            post("/api/v1/room/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(room)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.number", is(5)));
    assertEquals(6, roomRepository.findAll().size());
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "USER")
  public void unauthorizedAdd() throws Exception {
    Room room = new Room(5, 1239, roomTypeRepository.findById(1).get());
    mockMvc
        .perform(
            post("/api/v1/room/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(room)))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "ADMIN")
  public void delete() throws Exception {
    mockMvc
        .perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete(
                    "/api/v1/room/delete/1")
                .contentType("application/json"))
        .andExpect(status().isOk());
    assertEquals(4, roomRepository.findAll().size());
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "USER")
  public void unauthorizedDelete() throws Exception {
    mockMvc
        .perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete(
                    "/api/v1/room/delete/1")
                .contentType("application/json"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void getAll() throws Exception {
    mockMvc
        .perform(get("/api/v1/room/getAll"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.length()", is(5)));
  }

  @Test
  public void getRoomAvailabilityFalse() throws Exception {
    mockMvc
        .perform(get("/api/v1/room/room-available/1/2028-10-10/2028-10-12"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data", is(false)));
  }

  @Test
  public void getRoomAvailabilityTrue() throws Exception {
    mockMvc
        .perform(get("/api/v1/room/room-available/1/2028-10-15/2028-10-18"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data", is(true)));
  }

  @Test
  public void getAllByAvailableTimeAndTypeNotOccupied() throws Exception {
    mockMvc
        .perform(get("/api/v1/room/available/2028-10-15/2028-10-18/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.length()", is(5)));
  }

  @Test
  public void getAllByAvailableTimeAndTypeOccupied() throws Exception {
    mockMvc
        .perform(get("/api/v1/room/available/2028-10-10/2028-10-12/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.length()", is(4)));
  }

  @Test
  public void getAllRoomTypes() throws Exception {
    mockMvc
        .perform(get("/api/v1/room/all-room-type"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.length()", is(1)));
  }

  @Test
  public void getAllRoomTypesByAvailabilityAndOccupancy() throws Exception {
    mockMvc
        .perform(get("/api/v1/room/available-room-type/2028-10-15/2028-10-18/3"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.length()", is(1)));
  }

  @Test
  public void getAllRoomTypesByAvailabilityAndOccupancyToBigOccupancy() throws Exception {
    mockMvc
        .perform(get("/api/v1/room/available-room-type/2028-10-15/2028-10-18/6"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.length()", is(0)));
  }

  @Test
  public void getAllRoomByAvailabilityNotOccupied() throws Exception {
    mockMvc
        .perform(get("/api/v1/room/available-rooms/2028-10-15/2028-10-18"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.length()", is(5)));
  }

  @Test
  public void getAllRoomByAvailabilityOccupied() throws Exception {
    mockMvc
        .perform(get("/api/v1/room/available-rooms/2028-10-10/2028-10-12"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.length()", is(4)));
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "ADMIN")
  public void updatePrice() throws Exception {
    List<RoomType> roomTypes = roomTypeRepository.findAll();
    roomTypes.get(0).setPrice(200);

    mockMvc
        .perform(
            patch("/api/v1/room/update-price")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(roomTypes)))
        .andExpect(status().isOk());
    assertEquals(200, roomTypeRepository.findById(1).get().getPrice());
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "USER")
  public void unauthorizedUpdatePrice() throws Exception {
    List<RoomType> roomTypes = roomTypeRepository.findAll();
    roomTypes.get(0).setPrice(200);

    mockMvc
        .perform(
            patch("/api/v1/room/update-price")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(roomTypes)))
        .andExpect(status().isUnauthorized());
  }
}
