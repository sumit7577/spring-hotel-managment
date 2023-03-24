package com.hotel.jorvik.models.DTO;

import lombok.Data;

@Data
public class PasswordChangeDTO {
    private String password;
    private String newPassword;
}
