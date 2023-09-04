package com.hotel.jorvik.models;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;
import jakarta.validation.constraints.*;

@Data
@Entity
@Table(name = "Menu_Item")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "menu_date", nullable = false)
    private Date menuDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_type_id")
    private MenuType menuType;

    public MenuItem() {
    }

    public MenuItem(Date menuDate, Dish dish, MenuType menuType) {
        this.menuDate = menuDate;
        this.dish = dish;
        this.menuType = menuType;
    }
}
