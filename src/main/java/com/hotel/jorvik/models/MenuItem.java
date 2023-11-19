package com.hotel.jorvik.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Date;
import lombok.Data;

/**
 * Represents an item on a menu, including its date, associated dish, and menu type. This entity is
 * mapped to the "Menu_Item" table in the database. It links a specific dish to a date and a type of
 * menu, indicating when and in what context the dish is served.
 */
@Data
@Entity
@Table(name = "Menu_Item")
public class MenuItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "menu_date", nullable = false)
  private Date menuDate;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "dish_id")
  private Dish dish;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "menu_type_id")
  private MenuType menuType;

  public MenuItem() {}

  /**
   * Constructs a new MenuItem instance with the provided menu details.
   *
   * @param menuDate The date when the dish is on the menu.
   * @param dish The dish associated with this menu item.
   * @param menuType The type of menu (e.g., breakfast, lunch) to which this item belongs.
   */
  public MenuItem(Date menuDate, Dish dish, MenuType menuType) {
    this.menuDate = menuDate;
    this.dish = dish;
    this.menuType = menuType;
  }
}
