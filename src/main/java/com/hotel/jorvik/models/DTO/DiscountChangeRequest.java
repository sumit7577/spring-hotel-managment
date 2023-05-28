package com.hotel.jorvik.models.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class DiscountChangeRequest {
    @Min(value = 0, message = "Discount must be a non-negative number")
    @Max(value = 100, message = "Discount cannot be greater than 100")
    private int discount;
}
