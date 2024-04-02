package com.hotel.managment.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Represents a type of menu, such as breakfast, lunch, or dinner. This entity is mapped to the
 * "Menu_Type" table in the database. It utilizes an enumeration to define the specific type of
 * menu.
 */
@Data
@Entity
@Table(name = "Menu_Type")
public class MenuType {

  /**
   * Enum representing different user roles in the system.
   *
   * <p>This enum defines the various user roles that can be assigned for access control and
   * permissions, including ROLE_USER, ROLE_ADMIN, ROLE_CLEANER, and ROLE_RESTAURANT.
   */
  public enum MenuEnum {
    BREAKFAST,
    LUNCH,
    DINNER
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Enumerated(EnumType.STRING)
  @Column(name = "name", nullable = false)
  private MenuEnum name;

  public MenuType() {}

  public MenuType(MenuEnum name) {
    this.name = name;
  }
}
