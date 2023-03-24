package com.hotel.jorvik.repositories;

import com.hotel.jorvik.models.Weekend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WeekendRepository extends JpaRepository<Weekend, Integer> {
    @Query(value = "SELECT * FROM weekend WHERE DATE(date_from) = DATE(:date)", nativeQuery = true)
    Iterable<Weekend> findByDate(@Param("date") String date);
}
