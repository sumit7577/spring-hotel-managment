package com.hotel.jorvik.repositories;

import com.hotel.jorvik.models.MenuType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuTypeRepository extends JpaRepository<MenuType, Integer> {
    MenuType findByName(MenuType.EMenu menuTypeName);
}
