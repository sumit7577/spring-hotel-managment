package com.hotel.jorvik.services;

import com.hotel.jorvik.models.*;
import com.hotel.jorvik.models.DTO.auth.AuthenticationResponse;
import com.hotel.jorvik.models.DTO.user.*;
import com.hotel.jorvik.repositories.*;
import com.hotel.jorvik.security.EmailService;
import com.hotel.jorvik.security.JwtService;
import com.hotel.jorvik.security.SecurityTools;
import com.hotel.jorvik.services.implementation.UserServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private SecurityTools securityTools;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserServiceImp userService;

    @Test
    public void getAllTest() {
        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<UserDTO> result = userService.getAll();

        assertEquals(2, result.size());
    }

    @Test
    public void getByIdTest() {
        int id = 1;
        User user = new User();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UserDTO result = userService.getById(id);

        assertNotNull(result);
    }

    @Test
    public void checkEmailVerificationTest() {
        User user = new User();
        user.setVerified(new Timestamp(System.currentTimeMillis()));
        when(securityTools.retrieveUserData()).thenReturn(user);

        when(securityTools.retrieveUserData()).thenReturn(user);

        boolean result = userService.checkEmailVerification();

        assertTrue(result);
    }

    @Test
    public void getByMatchingTest() {
        String name = "John";
        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);

        Pageable limit = PageRequest.of(0, 5);
        when(userRepository.findByFirstNameContainingOrLastNameContaining(name, name, limit)).thenReturn(users);

        List<UserDTO> result = userService.getByMatching(name);

        assertEquals(2, result.size());
    }

    @Test
    public void updateEmailTest() {
        User user = new User();
        when(securityTools.retrieveUserData()).thenReturn(user);

        verify(emailService).sendConfirmationEmail(any(User.class));
        verify(jwtService).generateToken(any(User.class));
    }

    @Test
    public void updatePhoneTest() {
        PhoneChangeRequest phoneChangeRequest = new PhoneChangeRequest("1234567890");
        User user = new User();

        when(securityTools.isValidPhone(anyString())).thenReturn(true);
        when(securityTools.retrieveUserData()).thenReturn(user);

        userService.updatePhone(phoneChangeRequest);

        verify(userRepository).save(any(User.class));
    }

    @Test
    public void updateDiscountTest() {
        int id = 1;
        DiscountChangeRequest discountChangeRequest = new DiscountChangeRequest(12);
        User user = new User();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        userService.updateDiscount(id, discountChangeRequest);
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void getUserRoomReservationsCountTest() {
        User user = new User();
        user.setRoomReservations(Arrays.asList(new RoomReservation(), new RoomReservation()));

        when(securityTools.retrieveUserData()).thenReturn(user);

        int count = userService.getUserRoomReservationsCount();
        assertEquals(2, count);
    }

    @Test
    public void getUserEntertainmentReservationsCountTest() {
        User user = new User();
        user.setEntertainmentReservations(Arrays.asList(new EntertainmentReservation(), new EntertainmentReservation()));

        when(securityTools.retrieveUserData()).thenReturn(user);

        int count = userService.getUserEntertainmentReservationsCount();
        assertEquals(2, count);
    }
}
