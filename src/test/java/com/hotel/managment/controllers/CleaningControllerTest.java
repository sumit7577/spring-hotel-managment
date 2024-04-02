package com.hotel.managment.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.managment.models.dto.cleaning.CleanRoomRequest;
import com.hotel.managment.models.Room;
import com.hotel.managment.models.RoomType;
import com.hotel.managment.repositories.CleaningHistoryRepository;
import com.hotel.managment.repositories.RoomRepository;
import com.hotel.managment.repositories.RoomTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.config.name=application-test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CleaningControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private RoomTypeRepository roomTypeRepository;
  @Autowired private RoomRepository roomRepository;
  @Autowired private CleaningHistoryRepository cleaningHistoryRepository;

  @BeforeEach
  public void setup() {
    RoomType roomType = new RoomType(4, 100, 100);
    roomTypeRepository.save(roomType);
    Room room1 = new Room(1, 1234, roomType);
    Room room2 = new Room(2, 1235, roomType);
    Room room3 = new Room(3, 1236, roomType);
    Room room4 = new Room(4, 1237, roomType);
    Room room5 = new Room(5, 1238, roomType);
    roomRepository.saveAll(List.of(room1, room2, room3, room4, room5));
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "CLEANER")
  public void getRoomsToClean() throws Exception {
    mockMvc
        .perform(get("/api/v1/cleaning/getRoomsToClean"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.length()", is(0)));
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "USER")
  public void unauthorizedGetRoomsToClean() throws Exception {
    mockMvc.perform(get("/api/v1/cleaning/getRoomsToClean")).andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "CLEANER")
  public void cleanRoom() throws Exception {
    CleanRoomRequest cleanRoomRequest = new CleanRoomRequest(1);
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(cleanRoomRequest);

    assertEquals(0, cleaningHistoryRepository.findAll().size());
    mockMvc
        .perform(
            post("/api/v1/cleaning/cleanRoom")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
        .andExpect(status().isOk());
    assertEquals(1, cleaningHistoryRepository.findAll().size());
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "USER")
  public void unauthorizedCleanRoom() throws Exception {
    CleanRoomRequest cleanRoomRequest = new CleanRoomRequest(1);
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(cleanRoomRequest);

    mockMvc
        .perform(
            post("/api/v1/cleaning/cleanRoom")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
        .andExpect(status().isUnauthorized());
  }
}
