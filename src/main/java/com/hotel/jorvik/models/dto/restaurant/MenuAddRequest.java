package com.hotel.jorvik.models.dto.restaurant;

import com.hotel.jorvik.models.MenuType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request to add a menu item, specifying the item's identifier, date, and menu type.
 * This class is typically used to request the addition of a menu item, providing details such as
 * its identifier, date of availability, and the type of menu it belongs to.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuAddRequest {
  private Integer id;
  private String date;
  private MenuType.MenuEnum menuTypeName;
}
