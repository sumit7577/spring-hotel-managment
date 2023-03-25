package com.hotel.jorvik.models.DTO;

import lombok.Data;

@Data
public class PasswordChangeRequest {
    private String password;
    private String newPassword;
}
