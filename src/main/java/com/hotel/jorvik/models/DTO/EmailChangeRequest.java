package com.hotel.jorvik.models.DTO;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class EmailChangeRequest {
    @Email
    private String email;
}
