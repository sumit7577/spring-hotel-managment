package com.hotel.jorvik.models.DTO;

import com.hotel.jorvik.models.User;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserDTO {

    int id;
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name cannot be less that 3 and more than 50 characters")
    private String firstName;

    @NotBlank(message = "LastName is required")
    @Size(min = 3, max = 50, message = "LastName cannot be less that 3 and more than 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    @Size(min = 3, max = 100, message = "Email must be 3-100 characters long")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Size(max = 20, message = "Phone number must be at most 20 characters long")
    @Pattern(regexp = "^\\+?[0-9\\s]*$", message = "Phone number must contain only digits and spaces")
    private String phone;

    @NotNull
    @Min(value = 0, message = "Discount must be a non-negative number")
    @Max(value = 100, message = "Discount cannot be greater than 100")
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
