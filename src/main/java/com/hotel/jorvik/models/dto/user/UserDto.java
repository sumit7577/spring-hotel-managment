package com.hotel.jorvik.models.dto.user;

import com.hotel.jorvik.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Data Transfer Object (DTO) for user-related data, providing a simplified view of
 * user information. This class is typically used to transfer user data between different parts of
 * the application while providing a simplified representation of user details.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

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

  @Min(value = 0, message = "Discount must be a non-negative number")
  @Max(value = 100, message = "Discount cannot be greater than 100")
  private int discount;

  /**
   * Constructs a new UserDto instance with the provided user's details.
   *
   * @param user The user entity from which to create the DTO.
   */
  public UserDto(User user) {
    this.id = user.getId();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.email = user.getEmail();
    this.phone = user.getPhone();
    this.discount = user.getDiscount();
  }
}
