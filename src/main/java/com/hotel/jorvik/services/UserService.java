package com.hotel.jorvik.services;

import com.hotel.jorvik.models.DTO.*;
import com.hotel.jorvik.security.AuthenticationResponse;

import java.util.List;

public interface UserService {
    List<UserDTO> getAll();
    UserDTO getById(int id);
    UserDTO getByToken();
    boolean checkEmailVerification();
    void updatePassword(PasswordChangeRequest passwordChangeRequest);
    AuthenticationResponse updateEmail(EmailChangeRequest emailChangeRequest);
    void resentEmailVerification();
    void updatePhone(PhoneChangeRequest phoneChangeRequest);
    void updateDiscount(int id, DiscountChangeRequest discountChangeRequest);
}
