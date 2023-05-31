package com.hotel.jorvik.repositories;

import com.hotel.jorvik.models.Weekend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WeekendRepository extends JpaRepository<Weekend, Integer> {
    @Query(value = "SELECT w FROM Weekend w WHERE DATE(w.dateFrom) = DATE(:date)")
    Iterable<Weekend> findByDate(@Param("date") String date);
}
