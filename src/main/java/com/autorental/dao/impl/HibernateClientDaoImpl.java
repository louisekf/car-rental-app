package com.autorental.dao.impl;

import com.autorental.db.HibernateConnection;
import com.autorental.model.Client;
import org.hibernate.Session;

public class HibernateClientDaoImpl extends HibernateObjectDaoImpl<Client>{
    public HibernateClientDaoImpl() {
        super(Client.class);
    }

    public Client getClientByUserLogin(String login) {
        try (Session session = HibernateConnection.getInstance().openSession()) {
            return session.createQuery("FROM Client WHERE email = :login", Client.class)
                    .setParameter("login", login)
                    .uniqueResult();
        }
    }

}
