package com.autorental.dao.impl;

import com.autorental.model.Client;
import com.autorental.db.HibernateConnection;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ClientDaoImpl {

    public void save(Client client) {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getInstance().openSession()) {
            transaction = session.beginTransaction();
            session.save(client);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw e;
        }
    }

    public boolean emailExists(String email) {
        try (Session session = HibernateConnection.getInstance().openSession()) {
            Long count = session.createQuery(
                            "SELECT COUNT(c) FROM Client c WHERE c.email = :email", Long.class)
                    .setParameter("email", email)
                    .uniqueResult();
            return count != null && count > 0;
        }
    }
}
