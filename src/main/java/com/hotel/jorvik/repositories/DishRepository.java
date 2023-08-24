package com.hotel.jorvik.repositories;

import com.hotel.jorvik.models.Dish;
import com.hotel.jorvik.models.MenuType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Integer> {
    @Query("SELECT d FROM Dish d " +
            "JOIN MenuItem mi ON d.id = mi.dish.id " +
            "JOIN MenuType mt ON mt.id = mi.menuType.id " +
            "WHERE mt.name = :menuTypeName AND mi.menuDate = :menuDate")
    List<Dish> findDishesByMenuTypeNameAndMenuDate(MenuType.EMenu menuTypeName, Date menuDate);
}
