package com.hotel.jorvik.services.interfaces;

import com.hotel.jorvik.models.DTO.*;

public interface UserService {
    Iterable<UserDTO> getAll();
    UserDTO getById(int id);
    boolean updatePassword(PasswordChangeRequest passwordChangeRequest);
    boolean updateEmail(EmailChangeRequest emailChangeRequest);
    boolean resentEmailVerification();
    boolean updatePhone(PhoneChangeRequest phoneChangeRequest);
    boolean updateDiscount(int id, DiscountChangeRequest discountChangeRequest);
}
