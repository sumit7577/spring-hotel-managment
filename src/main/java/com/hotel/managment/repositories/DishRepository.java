package com.hotel.managment.repositories;

import com.hotel.managment.models.Dish;
import com.hotel.managment.models.MenuType;
import com.hotel.managment.models.dto.restaurant.MenuForDayResponse;
import jakarta.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for Dish entities. Extends JpaRepository to facilitate database operations
 * related to dishes. Includes custom queries for finding dishes based on menu type and date, as
 * well as modifying and deleting menu items.
 */
public interface DishRepository extends JpaRepository<Dish, Integer> {
  @Query(
      "SELECT d FROM Dish d "
          + "JOIN MenuItem mi ON d.id = mi.dish.id "
          + "JOIN MenuType mt ON mt.id = mi.menuType.id "
          + "WHERE mt.name = :menuTypeName AND mi.menuDate = :menuDate")
  List<Dish> findDishesByMenuTypeNameAndMenuDate(MenuType.MenuEnum menuTypeName, Date menuDate);

  @Query(
      "SELECT NEW com.hotel.jorvik.models.dto.restaurant.MenuForDayResponse(d.id, d.name, mt.name) "
          + "FROM Dish d "
          + "JOIN MenuItem mi ON d.id = mi.dish.id "
          + "JOIN MenuType mt ON mt.id = mi.menuType.id "
          + "WHERE mi.menuDate = :menuDate")
  List<MenuForDayResponse> findDishesByMenuDate(Date menuDate);

  @Modifying
  @Transactional
  @Query(
      "DELETE FROM MenuItem mi "
          + "WHERE mi.dish.id = :id "
          + "AND mi.menuDate = :dateSql "
          + "AND mi.menuType.id = (SELECT mt.id FROM MenuType mt WHERE mt.name = :menuTypeName)")
  void deleteFromDayMenu(
      @Param("dateSql") Date dateSql,
      @Param("id") Integer id,
      @Param("menuTypeName") MenuType.MenuEnum menuTypeName);
}
