package com.hotel.jorvik.repositories;

import com.hotel.jorvik.models.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for MenuItem entities. Extends JpaRepository to provide standard CRUD
 * operations for menu items.
 */
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {}
