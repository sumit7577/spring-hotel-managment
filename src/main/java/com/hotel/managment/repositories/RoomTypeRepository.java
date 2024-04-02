package com.hotel.managment.repositories;

import com.hotel.managment.models.RoomType;
import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for RoomType entities. Extends JpaRepository to facilitate database
 * operations for room types. Includes a custom query to find available room types based on room
 * occupancy and availability within a specified date range.
 */
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {
  @Query(
      "SELECT rt FROM RoomType rt WHERE rt.roomOccupancy >= :roomOccupancy AND rt.id IN ("
          + "SELECT r.roomType.id FROM Room r WHERE r.id NOT IN ("
          + "SELECT rr.room.id FROM RoomReservation rr WHERE "
          + "(rr.fromDate <= :dateTo AND rr.toDate >= :dateFrom) OR "
          + "(rr.fromDate >= :dateFrom AND rr.toDate <= :dateTo) OR "
          + "(rr.fromDate <= :dateFrom AND rr.toDate >= :dateTo)))")
  List<RoomType> findAvailableRoomTypesByOccupancy(
      @Param("dateFrom") Date dateFrom,
      @Param("dateTo") Date dateTo,
      @Param("roomOccupancy") int roomOccupancy);
}
