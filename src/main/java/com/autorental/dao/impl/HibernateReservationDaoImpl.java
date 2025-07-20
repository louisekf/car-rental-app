package com.autorental.dao.impl;

import com.autorental.model.Reservation;

public class HibernateReservationDaoImpl extends HibernateObjectDaoImpl<Reservation>{
    public HibernateReservationDaoImpl() {
        super(Reservation.class);
    }
}