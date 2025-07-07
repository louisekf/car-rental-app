package com.autorental.dao.impl;

import com.autorental.db.HibernateConnection;
import com.autorental.model.Client;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateClientDaoImpl {

    public boolean createClient(Client client) {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(client);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }
}
