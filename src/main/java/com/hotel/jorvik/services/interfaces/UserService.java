package com.hotel.jorvik.services.interfaces;

import com.hotel.jorvik.models.DTO.EmailChangeDTO;
import com.hotel.jorvik.models.DTO.PasswordChangeDTO;
import com.hotel.jorvik.models.DTO.UserDTO;

public interface UserService {
    Iterable<UserDTO> getAll();
    UserDTO getById(int id);
    boolean updateById(int id, UserDTO newUser);
    boolean updatePassword(PasswordChangeDTO passwordChangeDTO);
    boolean updateEmail(EmailChangeDTO emailChangeDTO);
    boolean resentEmailVerification();
}
