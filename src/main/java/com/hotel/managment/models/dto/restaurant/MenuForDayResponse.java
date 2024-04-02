package com.hotel.managment.models.dto.restaurant;

import com.hotel.managment.models.MenuType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a response containing details of a menu item for a specific day, including its
 * identifier, name, and menu type. This class is typically used to provide information about menu
 * items available for a particular day, presenting their identity, name, and menu type.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuForDayResponse {
  private Integer id;
  private String name;
  private MenuType.MenuEnum menuType;
}
