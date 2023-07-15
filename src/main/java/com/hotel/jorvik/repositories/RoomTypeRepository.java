package com.hotel.jorvik.repositories;

import com.hotel.jorvik.models.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {
    @Query("SELECT rt FROM RoomType rt WHERE rt.roomOccupancy >= :roomOccupancy AND rt.id IN (" +
            "SELECT r.roomType.id FROM Room r WHERE r.id NOT IN (" +
            "SELECT rr.room.id FROM RoomReservation rr WHERE " +
            "(DATE(rr.fromDate) <= DATE(:dateTo) AND DATE(rr.toDate) >= DATE(:dateFrom)) OR " +
            "(DATE(rr.fromDate) >= DATE(:dateFrom) AND DATE(rr.toDate) <= DATE(:dateTo)) OR " +
            "(DATE(rr.fromDate) <= DATE(:dateFrom) AND DATE(rr.toDate) >= DATE(:dateTo))))")
    List<RoomType> findAvailableRoomTypesByOccupancy(@Param("dateFrom") String dateFrom, @Param("dateTo") String dateTo, @Param("roomOccupancy") int roomOccupancy);
}
