package com.hotel.managment.repositories;

import com.hotel.managment.models.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for MenuItem entities. Extends JpaRepository to provide standard CRUD
 * operations for menu items.
 */
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {}
