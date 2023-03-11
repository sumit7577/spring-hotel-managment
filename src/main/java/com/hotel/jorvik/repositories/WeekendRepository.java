package com.hotel.jorvik.repositories;

import com.hotel.jorvik.models.Weekend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;

@Repository
public interface WeekendRepository extends JpaRepository<Weekend, Integer> {
    @Query(value = "SELECT * FROM weekend WHERE DATE(date_from) = :date", nativeQuery = true)
    Iterable<Weekend> findByDate(@Param("date") Date date);
}
