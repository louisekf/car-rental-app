package com.autorental.dao.impl;

import com.autorental.db.HibernateConnection;
import com.autorental.model.Client;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

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

    public List<Client> findByNomOuPrenom(String motCle) {
        Session session = HibernateConnection.getInstance().openSession();
        List<Client> clients = null;
        try {
            Query<Client> query = session.createQuery(
                    "FROM Client WHERE nom LIKE :motCle OR prenom LIKE :motCle", Client.class);
            query.setParameter("motCle", "%" + motCle + "%");
            clients = query.getResultList();
        } finally {
            session.close();
        }
        return clients;
    }


}
