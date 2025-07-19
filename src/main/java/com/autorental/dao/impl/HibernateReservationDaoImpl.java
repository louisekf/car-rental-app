package com.autorental.dao.impl;

import com.autorental.db.HibernateConnection;
import com.autorental.exceptions.DAOException;
import com.autorental.model.Reservation;
import org.hibernate.Session;

import java.util.List;

public class HibernateReservationDaoImpl extends HibernateObjectDaoImpl<Reservation>{
    public HibernateReservationDaoImpl() {
        super(Reservation.class);
    }

    public List listDecroissant() throws DAOException {
        try {
            Session session = HibernateConnection.getInstance().openSession();
            String query = "from Reservation order by id desc";
            return session.createQuery(query, Reservation.class).getResultList();
        } catch (Exception e) {
            throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
        }
    }
}
