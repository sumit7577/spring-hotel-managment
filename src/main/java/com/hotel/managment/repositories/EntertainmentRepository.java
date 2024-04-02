package com.hotel.managment.repositories;

import com.hotel.managment.models.Entertainment;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for Entertainment entities. Extends JpaRepository to provide standard CRUD
 * operations. Includes a custom query to find available entertainments by type and time,
 * considering existing reservations.
 */
public interface EntertainmentRepository extends JpaRepository<Entertainment, Integer> {

  @Query(
      "SELECT e FROM Entertainment e WHERE e.entertainmentType.id = :entertainmentTypeId AND "
          + "NOT EXISTS ("
          + "SELECT 1 FROM EntertainmentReservation er WHERE er.entertainment.id = e.id AND ("
          + "(er.dateFrom BETWEEN :startTime AND :endTime) OR "
          + "(er.dateTo BETWEEN :startTime AND :endTime) OR "
          + "(:startTime BETWEEN er.dateFrom AND er.dateTo) OR "
          + "(:endTime BETWEEN er.dateFrom AND er.dateTo)))")
  List<Entertainment> findAvailableEntertainmentsByTypeAndTime(
      @Param("entertainmentTypeId") int entertainmentTypeId,
      @Param("startTime") Timestamp startTime,
      @Param("endTime") Timestamp endTime);
}
