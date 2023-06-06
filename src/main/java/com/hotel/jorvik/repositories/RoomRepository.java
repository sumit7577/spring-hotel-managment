package com.hotel.jorvik.repositories;

import com.hotel.jorvik.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query("SELECT r FROM Room r WHERE r.id NOT IN (" +
            "SELECT rr.room.id FROM RoomReservation rr WHERE " +
            "(DATE(rr.fromDate) <= DATE(:dateTo) AND DATE(rr.toDate) >= DATE(:dateFrom)) OR " +
            "(DATE(rr.fromDate) >= DATE(:dateFrom) AND DATE(rr.toDate) <= DATE(:dateTo)) OR " +
            "(DATE(rr.fromDate) <= DATE(:dateFrom) AND DATE(rr.toDate) >= DATE(:dateTo)))")
    Iterable<Room> findAvailableRoomsByTime(@Param("dateFrom") String dateFrom, @Param("dateTo") String dateTo);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN 'false' ELSE 'true' END FROM Room r WHERE r.id = :roomId AND r.id IN (" +
            "SELECT rr.room.id FROM RoomReservation rr WHERE " +
            "(DATE(rr.fromDate) <= DATE(:dateTo) AND DATE(rr.toDate) >= DATE(:dateFrom)) OR " +
            "(DATE(rr.fromDate) >= DATE(:dateFrom) AND DATE(rr.toDate) <= DATE(:dateTo)) OR " +
            "(DATE(rr.fromDate) <= DATE(:dateFrom) AND DATE(rr.toDate) >= DATE(:dateTo)))")
    boolean isRoomAvailable(@Param("roomId") int roomId, @Param("dateFrom") String dateFrom, @Param("dateTo") String dateTo);
}
