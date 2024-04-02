package com.hotel.managment.repositories;

import com.hotel.managment.models.Room;
import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for Room entities. Extends JpaRepository to provide standard CRUD operations
 * for rooms. Includes custom queries to find available rooms based on time and type, check room
 * availability, and find rooms with active reservations that have not been cleaned today.
 */
public interface RoomRepository extends JpaRepository<Room, Integer> {
  @Query(
      "SELECT r FROM Room r WHERE r.id NOT IN ("
          + "SELECT rr.room.id FROM RoomReservation rr WHERE "
          + "(rr.fromDate <= :dateTo AND rr.toDate >= :dateFrom) OR "
          + "(rr.fromDate >= :dateFrom AND rr.toDate <= :dateTo) OR "
          + "(rr.fromDate <= :dateFrom AND rr.toDate >= :dateTo)) AND r.roomType.id = :roomTypeId")
  List<Room> findAvailableRoomsByTimeAndType(
      @Param("dateFrom") Date dateFrom,
      @Param("dateTo") Date dateTo,
      @Param("roomTypeId") int roomTypeId);

  @Query(
      "SELECT CASE WHEN COUNT(r) > 0 THEN 'false' ELSE 'true' END "
          + "FROM Room r WHERE r.id = :roomId AND r.id IN ("
          + "SELECT rr.room.id FROM RoomReservation rr WHERE "
          + "(rr.fromDate <= :dateTo AND rr.toDate >= :dateFrom) OR "
          + "(rr.fromDate >= :dateFrom AND rr.toDate <= :dateTo) OR "
          + "(rr.fromDate <= :dateFrom AND rr.toDate >= :dateTo))")
  boolean isRoomAvailable(
      @Param("roomId") int roomId, @Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);

  @Query(
      "SELECT r FROM Room r WHERE r.id NOT IN ("
          + "SELECT rr.room.id FROM RoomReservation rr WHERE "
          + "(rr.fromDate <= :dateTo AND rr.toDate >= :dateFrom) OR "
          + "(rr.fromDate >= :dateFrom AND rr.toDate <= :dateTo) OR "
          + "(rr.fromDate <= :dateFrom AND rr.toDate >= :dateTo))")
  List<Room> findAvailableRoomsByTime(
      @Param("dateFrom") Date dateFromSql, @Param("dateTo") Date dateToSql);

  @Query(
      "SELECT r FROM Room r JOIN RoomReservation rr ON r.id = rr.room.id WHERE "
          + "rr.fromDate <= CURRENT_DATE AND rr.toDate >= CURRENT_DATE AND r.id NOT IN ("
          + "SELECT ch.room.id FROM CleaningHistory ch WHERE DATE(ch.cleanedAt) = CURRENT_DATE)")
  List<Room> findActiveReservationRoomsNotCleanedToday();
}
