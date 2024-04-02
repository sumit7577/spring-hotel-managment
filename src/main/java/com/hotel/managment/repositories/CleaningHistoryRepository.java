package com.hotel.managment.repositories;

import com.hotel.managment.models.CleaningHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for CleaningHistory entities. Extends JpaRepository to provide standard CRUD
 * operations.
 */
public interface CleaningHistoryRepository extends JpaRepository<CleaningHistory, Integer> {}
