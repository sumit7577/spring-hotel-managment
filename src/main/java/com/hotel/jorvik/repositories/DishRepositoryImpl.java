package com.hotel.jorvik.repositories;

import com.hotel.jorvik.models.Dish;
import org.hibernate.Session;
import jakarta.persistence.EntityManager;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DishRepositoryImpl implements DishRepository{

    @Autowired
    public EntityManager entityManager;

    @Override
    public List<Dish> getAllDishers() {
        Session session = entityManager.unwrap(Session.class);
        Query<Dish> query = session.createQuery("from Dish", Dish.class);
        return query.getResultList();
    }
}
