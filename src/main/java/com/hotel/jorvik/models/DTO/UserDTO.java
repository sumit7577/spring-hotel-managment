package com.hotel.jorvik.models.DTO;

import com.hotel.jorvik.models.User;
import lombok.Data;

@Data
public class UserDTO {

    int id;
    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private int discount;

    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.discount = user.getDiscount();
    }
}
