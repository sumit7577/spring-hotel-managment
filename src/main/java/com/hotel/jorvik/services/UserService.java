package com.hotel.jorvik.services;

import com.hotel.jorvik.models.dto.auth.AuthenticationResponse;
import com.hotel.jorvik.models.dto.user.DiscountChangeRequest;
import com.hotel.jorvik.models.dto.user.EmailChangeRequest;
import com.hotel.jorvik.models.dto.user.PasswordChangeRequest;
import com.hotel.jorvik.models.dto.user.PhoneChangeRequest;
import com.hotel.jorvik.models.dto.user.UserDto;
import com.hotel.jorvik.models.dto.user.UserUpdateRequest;
import java.util.List;

/**
 * Interface for managing user-related services in the application.
 *
 * <p>This interface provides methods for user management, including retrieving user information,
 * updating user details, and handling authentication-related aspects like email verification and
 * password changes. It also offers functionalities for managing user-specific data such as room and
 * entertainment reservations.
 */
public interface UserService {
  List<UserDto> getAll();

  UserDto getById(int id);

  UserDto getByToken();

  boolean checkEmailVerification();

  void updatePassword(PasswordChangeRequest passwordChangeRequest);

  AuthenticationResponse updateEmail(EmailChangeRequest emailChangeRequest);

  void resentEmailVerification();

  void updatePhone(PhoneChangeRequest phoneChangeRequest);

  void updateDiscount(int id, DiscountChangeRequest discountChangeRequest);

  int getUserRoomReservationsCount();

  int getUserEntertainmentReservationsCount();

  List<UserDto> getByMatching(String name);

  void update(Integer id, UserUpdateRequest userUpdateRequest);
}
