package com.hotel.managment.models.dto.user;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request to change a user's discount level, specifying the new discount value. This
 * class is typically used to request a change in a user's discount percentage by providing the
 * desired discount value.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiscountChangeRequest {
  @Min(value = 0, message = "Discount must be a non-negative number")
  @Max(value = 100, message = "Discount cannot be greater than 100")
  private int discount;
}
