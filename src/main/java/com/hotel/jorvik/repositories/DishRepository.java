package com.hotel.jorvik.repositories;

import com.hotel.jorvik.models.DTO.restaurant.MenuForDayResponse;
import com.hotel.jorvik.models.Dish;
import com.hotel.jorvik.models.MenuType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Integer> {
    @Query("SELECT d FROM Dish d " +
            "JOIN MenuItem mi ON d.id = mi.dish.id " +
            "JOIN MenuType mt ON mt.id = mi.menuType.id " +
            "WHERE mt.name = :menuTypeName AND mi.menuDate = :menuDate")
    List<Dish> findDishesByMenuTypeNameAndMenuDate(MenuType.EMenu menuTypeName, Date menuDate);

    @Query("SELECT NEW com.hotel.jorvik.models.DTO.restaurant.MenuForDayResponse(d.id, d.name, mt.name) " +
            "FROM Dish d " +
            "JOIN MenuItem mi ON d.id = mi.dish.id " +
            "JOIN MenuType mt ON mt.id = mi.menuType.id " +
            "WHERE mi.menuDate = :menuDate")
    List<MenuForDayResponse> findDishesByMenuDate(Date menuDate);

    @Modifying
    @Transactional
    @Query("DELETE FROM MenuItem mi " +
            "WHERE mi.dish.id = :id " +
            "AND mi.menuDate = :dateSql " +
            "AND mi.menuType.id = (SELECT mt.id FROM MenuType mt WHERE mt.name = :menuTypeName)")
    void deleteFromDayMenu(@Param("dateSql") Date dateSql, @Param("id") Integer id, @Param("menuTypeName") MenuType.EMenu menuTypeName);
}
