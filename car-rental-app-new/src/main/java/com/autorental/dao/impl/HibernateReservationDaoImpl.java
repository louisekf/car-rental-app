package com.autorental.dao.impl;

import com.autorental.db.HibernateConnection;
import com.autorental.model.Chauffeur;
import com.autorental.model.Reservation;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateReservationDaoImpl extends HibernateObjectDaoImpl<Reservation>{
    public HibernateReservationDaoImpl() {
        super(Reservation.class);
    }

    public List<Reservation> findAll() {
        try (Session session = HibernateConnection.getInstance().openSession()) {
            return session.createQuery("from Reservation", Reservation.class).list();
        }
    }


    public void update(Reservation reservation) {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getInstance().openSession()) {
            transaction = session.beginTransaction();
            session.merge(reservation);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
