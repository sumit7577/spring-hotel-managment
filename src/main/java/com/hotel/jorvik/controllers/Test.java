package com.hotel.jorvik.controllers;

import com.hotel.jorvik.models.Weekend;
import org.hibernate.Session;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class Test {

    private EntityManager entityManager;

    public void getWeekendProgram(){
        Session session = entityManager.unwrap(Session.class);

            }

}
