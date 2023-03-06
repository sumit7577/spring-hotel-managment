package com.hotel.jorvik.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "RoomType")
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "roomOccupancy", nullable = false)
    private int roomOccupancy;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "roomArea", nullable = false)
    private int roomArea;

    public RoomType() {
    }

    public RoomType(int roomOccupancy, int price, int roomArea) {
        this.roomOccupancy = roomOccupancy;
        this.price = price;
        this.roomArea = roomArea;
    }
}
