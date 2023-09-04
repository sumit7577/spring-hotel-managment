package com.hotel.jorvik.models.DTO.restaurant;

import com.hotel.jorvik.models.MenuType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuAddRequest {
    Integer id;
    String date;
    MenuType.EMenu menuTypeName;
}
