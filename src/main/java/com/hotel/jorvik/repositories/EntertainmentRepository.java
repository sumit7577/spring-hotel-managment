package com.hotel.jorvik.repositories;

import com.hotel.jorvik.models.Entertainment;
import com.hotel.jorvik.models.EntertainmentReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface EntertainmentRepository extends JpaRepository<Entertainment, Integer> {

    @Query("SELECT e FROM Entertainment e WHERE e.entertainmentType.id = :entertainmentTypeId AND " +
            "NOT EXISTS (" +
            "SELECT 1 FROM EntertainmentReservation er WHERE er.entertainment.id = e.id AND " +
            "((er.dateFrom BETWEEN :startTime AND :endTime) OR" +
            "(er.dateTo BETWEEN :startTime AND :endTime)))")
    List<Entertainment> findAvailableEntertainmentsByTypeAndTime(
            @Param("entertainmentTypeId") int entertainmentTypeId,
            @Param("startTime") Timestamp startTime,
            @Param("endTime") Timestamp endTime);
}
