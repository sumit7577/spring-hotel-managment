package com.hotel.jorvik.repositories;

import com.hotel.jorvik.models.CleaningHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CleaningHistoryRepository extends JpaRepository<CleaningHistory, Integer> {
}
