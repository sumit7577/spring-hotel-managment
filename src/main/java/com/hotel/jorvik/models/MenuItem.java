package com.hotel.jorvik.models;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;

@Data
@Entity
@Table(name = "MenuItem")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "menuDate", nullable = false)
    private Date menuDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dishID")
    private Dish dish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menuTypeID")
    private MenuType menuType;

    public MenuItem() {
    }

    public MenuItem(Date menuDate, Dish dish, MenuType menuType) {
        this.menuDate = menuDate;
        this.dish = dish;
        this.menuType = menuType;
    }
}
