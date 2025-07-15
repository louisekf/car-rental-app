package com.autorental.interfaces;

import com.autorental.dao.IDao;
import com.autorental.model.Reservation;
import com.autorental.model.User;

public interface ReservationFactory {
    IDao<Reservation> getReservationDao(Class<? extends IDao<Reservation>> daoReservation);
}
