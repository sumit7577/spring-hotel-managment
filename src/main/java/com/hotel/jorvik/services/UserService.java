package com.hotel.jorvik.services;

import com.hotel.jorvik.models.DTO.*;

public interface UserService {
    Iterable<UserDTO> getAll();
    UserDTO getById(int id);
    void updatePassword(PasswordChangeRequest passwordChangeRequest);
    void updateEmail(EmailChangeRequest emailChangeRequest);
    void resentEmailVerification();
    void updatePhone(PhoneChangeRequest phoneChangeRequest);
    void updateDiscount(int id, DiscountChangeRequest discountChangeRequest);
}
