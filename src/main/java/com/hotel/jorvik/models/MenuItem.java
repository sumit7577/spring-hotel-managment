package com.hotel.jorvik.models;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;
import jakarta.validation.constraints.*;

@Data
@Entity
@Table(name = "MenuItem")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @NotNull
    @Column(name = "menu_Date", nullable = false)
    @FutureOrPresent
    private Date menuDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_ID")
    private Dish dish;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menuType_ID")
    private MenuType menuType;

    public MenuItem() {
    }

    public MenuItem(Date menuDate, Dish dish, MenuType menuType) {
        this.menuDate = menuDate;
        this.dish = dish;
        this.menuType = menuType;
    }
}
