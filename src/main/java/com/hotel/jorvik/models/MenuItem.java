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

    @NotNull
    @Column(name = "menu_date", nullable = false)
    @FutureOrPresent
    private Date menuDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
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
