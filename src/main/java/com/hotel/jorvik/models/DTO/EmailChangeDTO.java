package com.hotel.jorvik.models.DTO;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class EmailChangeDTO {
    @Email
    private String email;
}
