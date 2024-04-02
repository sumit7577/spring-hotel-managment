package com.hotel.managment.controllers;

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
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "spring.config.name=application-test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookingControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private RoomTypeRepository roomTypeRepository;
  @Autowired private RoomRepository roomRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private RoleRepository roleRepository;
  @Autowired private RoomReservationRepository roomReservationRepository;
  @Autowired private EntertainmentTypeRepository entertainmentTypeRepository;
  @Autowired private EntertainmentRepository entertainmentRepository;
  @Autowired private EntertainmentReservationRepository entertainmentReservationRepository;
  private User testUser;

  @BeforeEach
  public void setup() {
    this.testUser = setupTestUser();
  }

  private User setupTestUser() {
    Role role = new Role(Role.RoleEnum.ROLE_USER);
    roleRepository.save(role);
    User user = new User("test", "test", "test@example.com", "123456789", 0, "test", role);
    userRepository.save(user);
    return user;
  }

  private void setupTestRoom() {
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
            testUser);
    roomReservationRepository.save(roomReservation);
  }

  private void setupTestEntertainment() {
    EntertainmentType entertainmentType1 = new EntertainmentType("Tennis", 100);
    EntertainmentType entertainmentType2 = new EntertainmentType("Kayak", 200);
    entertainmentTypeRepository.save(entertainmentType1);
    entertainmentTypeRepository.save(entertainmentType2);
    Entertainment entertainment1 = new Entertainment("Tennis1", 122, entertainmentType1);
    Entertainment entertainment2 = new Entertainment("Tennis2", 122, entertainmentType1);
    Entertainment entertainment3 = new Entertainment("Tennis3", 122, entertainmentType1);
    entertainmentRepository.saveAll(List.of(entertainment1, entertainment2, entertainment3));
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime dateTime = LocalDateTime.parse("2028-10-10 07:00", formatter);
    Timestamp timestampFrom = Timestamp.valueOf(dateTime);
    dateTime = LocalDateTime.parse("2028-10-10 13:00", formatter);
    Timestamp timestampTo = Timestamp.valueOf(dateTime);
    EntertainmentReservation entertainmentReservation =
        new EntertainmentReservation(
            timestampFrom,
            timestampTo,
            new Timestamp(System.currentTimeMillis()),
            testUser,
            entertainment1);
    entertainmentReservationRepository.save(entertainmentReservation);
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "USER")
  public void bookRoom() throws Exception {
    setupTestRoom();
    MvcResult result =
        mockMvc
            .perform(get("/api/v1/bookings/room/2028-09-05/2028-09-06/2"))
            .andExpect(status().is4xxClientError())
            .andReturn();
    String responseJson = result.getResponse().getContentAsString();
    assertEquals("{\"status\":\"fail\",\"data\":\"No rooms available\"}", responseJson);
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "USER")
  public void bookRoomOccupied() throws Exception {
    setupTestRoom();
    mockMvc
        .perform(get("/api/v1/bookings/room/2028-09-05/2028-09-06/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.payment", is(nullValue())))
        .andExpect(jsonPath("$.data.fromDate", is("2028-09-05")))
        .andExpect(jsonPath("$.data.toDate", is("2028-09-06")))
        .andExpect(jsonPath("$.data.room.number", is(1)))
        .andExpect(jsonPath("$.data.room.accessCode", is(1234)))
        .andExpect(jsonPath("$.data.room.roomType.roomOccupancy", is(4)))
        .andExpect(jsonPath("$.data.room.roomType.price", is(100)));
  }

  @Test
  public void unauthorizedBookRoom() throws Exception {
    setupTestRoom();
    mockMvc
        .perform(get("/api/v1/bookings/room/2028-09-05/2028-09-06/1"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "ADMIN")
  public void bookRoomByAdmin() throws Exception {
    setupTestRoom();
    mockMvc
        .perform(get("/api/v1/bookings/room-by-admin/2028-09-05/2028-09-06/1/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.payment", is(nullValue())))
        .andExpect(jsonPath("$.data.fromDate", is("2028-09-05")))
        .andExpect(jsonPath("$.data.toDate", is("2028-09-06")))
        .andExpect(jsonPath("$.data.room.number", is(1)))
        .andExpect(jsonPath("$.data.room.accessCode", is(1234)))
        .andExpect(jsonPath("$.data.room.roomType.roomOccupancy", is(4)))
        .andExpect(jsonPath("$.data.room.roomType.price", is(100)));
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "USER")
  public void unauthorizedBookRoomByAdmin() throws Exception {
    setupTestRoom();
    mockMvc
        .perform(get("/api/v1/bookings/room-by-admin/2028-09-05/2028-09-06/1/1"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "USER")
  public void bookEntertainment() throws Exception {
    setupTestEntertainment();
    mockMvc
        .perform(get("/api/v1/bookings/entertainment/Tennis/2028-09-05/2028-09-06/17-00/18-00/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.payment", is(nullValue())))
        .andExpect(jsonPath("$.data.dateFrom", is("2028-09-05T15:00:00.000+00:00")))
        .andExpect(jsonPath("$.data.dateTo", is("2028-09-06T16:00:00.000+00:00")))
        .andExpect(jsonPath("$.data.entertainment.description", is("Tennis1")))
        .andExpect(jsonPath("$.data.entertainment.lockCode", is(122)))
        .andExpect(jsonPath("$.data.entertainment.entertainmentType.name", is("Tennis")))
        .andExpect(jsonPath("$.data.entertainment.entertainmentType.price", is(100)));
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "USER")
  public void bookEntertainmentOccupied() throws Exception {
    setupTestEntertainment();
    MvcResult result =
        mockMvc
            .perform(
                get("/api/v1/bookings/entertainment/Kayak/2028-09-05/2028-09-06/17-00/18-00/1"))
            .andExpect(status().is4xxClientError())
            .andReturn();
    String responseJson = result.getResponse().getContentAsString();
    assertEquals("{\"status\":\"fail\",\"data\":\"Entertainment not found\"}", responseJson);
  }

  @Test
  public void unauthorizedBookEntertainment() throws Exception {
    setupTestEntertainment();
    mockMvc
        .perform(get("/api/v1/bookings/entertainment/Tennis/2028-09-05/2028-09-06/17-00/18-00/1"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "USER")
  public void getLastBooking() throws Exception {
    setupTestRoom();
    mockMvc
        .perform(get("/api/v1/bookings/room/getLastBooking"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.number", is(1)))
        .andExpect(jsonPath("$.data.accessCode", is(1234)))
        .andExpect(jsonPath("$.data.roomType.roomOccupancy", is(4)));
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "ADMIN")
  public void getBookingsForPeriod() throws Exception {
    setupTestRoom();
    mockMvc
        .perform(get("/api/v1/bookings/room/getBookingsForPeriod/2028-10-09/2028-10-11"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.length()", is(1)))
        .andExpect(jsonPath("$.data[0].clientName", is("test test")))
        .andExpect(jsonPath("$.data[0].roomNumber", is(1)))
        .andExpect(jsonPath("$.data[0].bookingStatus", is("AWAITING_PAYMENT")))
        .andExpect(jsonPath("$.data[0].rate", is(200)));
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "USER")
  public void unauthorizedGetBookingsForPeriod() throws Exception {
    setupTestRoom();
    mockMvc
        .perform(get("/api/v1/bookings/room/getBookingsForPeriod/2028-10-09/2028-10-11"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "ADMIN")
  public void getEntertainmentBookingsForPeriod() throws Exception {
    setupTestEntertainment();
    mockMvc
        .perform(get("/api/v1/bookings/getEntertainmentBookingsForPeriod/2028-10-09/2028-10-11"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.length()", is(1)))
        .andExpect(jsonPath("$.data[0].clientName", is("test test")))
        .andExpect(jsonPath("$.data[0].entertainmentType", is("Tennis")))
        .andExpect(jsonPath("$.data[0].bookingStatus", is("AWAITING_PAYMENT")))
        .andExpect(jsonPath("$.data[0].rate", is(600)));
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "USER")
  public void unauthorizedGetEntertainmentBookingsForPeriod() throws Exception {
    setupTestEntertainment();
    mockMvc
        .perform(get("/api/v1/bookings/getEntertainmentBookingsForPeriod/2028-10-09/2028-10-11"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "USER")
  public void getAllCurrentRooms() throws Exception {
    setupTestRoom();
    mockMvc
        .perform(get("/api/v1/bookings/room/getAllCurrentRooms"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.length()", is(0)));
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "USER")
  public void getAll() throws Exception {
    setupTestRoom();
    setupTestEntertainment();
    mockMvc
        .perform(get("/api/v1/bookings/getAll"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.length()", is(2)));
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "ADMIN")
  public void getAllByAdmin() throws Exception {
    setupTestRoom();
    setupTestEntertainment();
    mockMvc
        .perform(get("/api/v1/bookings/getAllByAdmin/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.length()", is(2)));
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "USER")
  public void unauthorizedGetAllByAdmin() throws Exception {
    setupTestRoom();
    setupTestEntertainment();
    mockMvc.perform(get("/api/v1/bookings/getAllByAdmin/1")).andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "USER")
  public void deleteBooking() throws Exception {
    setupTestRoom();
    mockMvc.perform(delete("/api/v1/bookings/deleteBooking/1")).andExpect(status().isOk());
    assertEquals(0, roomReservationRepository.findAll().size());
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "ADMIN")
  public void deleteBookingByAdmin() throws Exception {
    setupTestRoom();
    mockMvc.perform(delete("/api/v1/bookings/deleteBookingByAdmin/1")).andExpect(status().isOk());
    assertEquals(0, roomReservationRepository.findAll().size());
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "USER")
  public void unauthorizedDeleteBookingByAdmin() throws Exception {
    setupTestRoom();
    mockMvc
        .perform(delete("/api/v1/bookings/deleteBookingByAdmin/1"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "USER")
  public void deleteEntertainmentBooking() throws Exception {
    setupTestEntertainment();
    mockMvc
        .perform(delete("/api/v1/bookings/deleteEntertainmentBooking/1"))
        .andExpect(status().isOk());
    assertEquals(0, entertainmentReservationRepository.findAll().size());
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "ADMIN")
  public void deleteEntertainmentBookingByAdmin() throws Exception {
    setupTestEntertainment();
    mockMvc
        .perform(delete("/api/v1/bookings/deleteEntertainmentBookingByAdmin/1"))
        .andExpect(status().isOk());
    assertEquals(0, entertainmentReservationRepository.findAll().size());
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "USER")
  public void unauthorizedDeleteEntertainmentBookingByAdmin() throws Exception {
    setupTestEntertainment();
    mockMvc
        .perform(delete("/api/v1/bookings/deleteEntertainmentBookingByAdmin/1"))
        .andExpect(status().isUnauthorized());
  }
}
