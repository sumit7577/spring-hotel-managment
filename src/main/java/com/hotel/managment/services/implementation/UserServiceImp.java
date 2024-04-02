package com.hotel.managment.services.implementation;

import com.hotel.managment.models.TokenType;
import com.hotel.managment.models.User;
import com.hotel.managment.models.dto.auth.AuthenticationResponse;
import com.hotel.managment.models.dto.user.DiscountChangeRequest;
import com.hotel.managment.models.dto.user.EmailChangeRequest;
import com.hotel.managment.models.dto.user.PasswordChangeRequest;
import com.hotel.managment.models.dto.user.PhoneChangeRequest;
import com.hotel.managment.models.dto.user.UserDto;
import com.hotel.managment.models.dto.user.UserUpdateRequest;
import com.hotel.managment.repositories.UserRepository;
import com.hotel.managment.security.EmailService;
import com.hotel.managment.security.JwtService;
import com.hotel.managment.security.SecurityTools;
import com.hotel.managment.services.UserService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * Implementation for managing user-related services in the application.
 *
 * <p>This service provides methods for user management, including retrieving user information,
 * updating user details, and handling authentication-related aspects like email verification and
 * password changes. It also offers functionalities for managing user-specific data such as room and
 * entertainment reservations.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final EmailService emailService;
  private final SecurityTools securityTools;
  private final JwtService jwtService;

  @Override
  public List<UserDto> getAll() {
    Iterable<User> users = userRepository.findAll();
    return StreamSupport.stream(users.spliterator(), false)
        .map(UserDto::new)
        .collect(Collectors.toList());
  }

  @Override
  public List<UserDto> getByMatching(String name) {
    Pageable limit = PageRequest.of(0, 5);
    String[] parts = name.split(" ", 2);

    List<User> users;
    if (parts.length == 2) {
      String firstNamePart = parts[0];
      String lastNamePart = parts[1];
      users =
          userRepository.findByFirstNameContainingAndLastNameContaining(
              firstNamePart, lastNamePart, limit);
    } else {
      users = userRepository.findByFirstNameContainingOrLastNameContaining(name, name, limit);
    }

    return users.stream().map(UserDto::new).collect(Collectors.toList());
  }

  @Override
  public UserDto getById(int id) {
    Optional<User> user = userRepository.findById(id);
    if (user.isEmpty()) {
      throw new NoSuchElementException("User not found");
    }
    return user.map(UserDto::new).get();
  }

  @Override
  public UserDto getByToken() {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    Optional<User> user = userRepository.findByEmail(email);
    if (user.isEmpty()) {
      throw new NoSuchElementException("User not found");
    }
    return user.map(UserDto::new).get();
  }

  @Override
  public boolean checkEmailVerification() {
    User user = securityTools.retrieveUserData();
    return user.getVerified() != null;
  }

  @Override
  @Transactional
  public void updatePassword(PasswordChangeRequest passwordChangeRequest) {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();

    if (!securityTools.isValidPassword(passwordChangeRequest.getNewPassword())) {
      throw new IllegalArgumentException("Password is not valid");
    }
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(email, passwordChangeRequest.getPassword()));
    User user = securityTools.retrieveUserData();
    user.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
    userRepository.save(user);
  }

  @Override
  @Transactional
  public AuthenticationResponse updateEmail(EmailChangeRequest emailChangeRequest) {
    userRepository
        .findByEmail(emailChangeRequest.getEmail())
        .ifPresent(
            row -> {
              throw new IllegalArgumentException("Email already exists");
            });

    User user = securityTools.retrieveUserData();

    user.setEmail(emailChangeRequest.getEmail());
    user.setVerified(null);
    emailService.sendConfirmationEmail(user);
    userRepository.save(user);

    String jwtToken = jwtService.generateToken(user);
    jwtService.revokeAllUserTokens(user, TokenType.TokenTypeEnum.ACCESS);
    jwtService.saveUserToken(user, jwtToken, TokenType.TokenTypeEnum.ACCESS);

    return AuthenticationResponse.builder().token(jwtToken).build();
  }

  @Override
  @Transactional
  public void resentEmailVerification() {
    User user = securityTools.retrieveUserData();
    emailService.sendConfirmationEmail(user);
  }

  @Override
  @Transactional
  public void updatePhone(PhoneChangeRequest phoneChangeRequest) {
    if (!securityTools.isValidPhone(phoneChangeRequest.getPhone())) {
      throw new IllegalArgumentException("Phone is not valid");
    }
    User user = securityTools.retrieveUserData();
    user.setPhone(phoneChangeRequest.getPhone());
    userRepository.save(user);
  }

  @Override
  @Transactional
  public void updateDiscount(int id, DiscountChangeRequest discountChangeRequest) {
    Optional<User> user = userRepository.findById(id);
    if (user.isEmpty()) {
      throw new NoSuchElementException("User not found");
    }
    user.get().setDiscount(discountChangeRequest.getDiscount());
    userRepository.save(user.get());
  }

  @Override
  public int getUserRoomReservationsCount() {
    User user = securityTools.retrieveUserData();
    return user.getRoomReservations().size();
  }

  @Override
  public int getUserEntertainmentReservationsCount() {
    User user = securityTools.retrieveUserData();
    return user.getEntertainmentReservations().size();
  }

  @Override
  public void update(Integer id, UserUpdateRequest userUpdateRequest) {
    Optional<User> user = userRepository.findById(id);
    if (user.isEmpty()) {
      throw new NoSuchElementException("User not found");
    }
    user.get().setFirstName(userUpdateRequest.getFirstName());
    user.get().setLastName(userUpdateRequest.getLastName());
    user.get().setEmail(userUpdateRequest.getEmail());
    user.get().setPhone(userUpdateRequest.getPhone());
    user.get().setDiscount(userUpdateRequest.getDiscount());
    userRepository.save(user.get());
  }
}
