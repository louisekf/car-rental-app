package com.autorental.dao.impl;

import com.autorental.dao.IDao;
import com.autorental.db.HibernateConnection;
import com.autorental.exceptions.DAOException;
import com.autorental.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateUserDaoImpl implements IDao<User> {

    @Override
    public void create(User user) throws DAOException {
        try {
            Session session = HibernateConnection.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            throw new DAOException("ERROR : " + e.getClass() + " : " + e.getMessage());
        }
    }

    @Override
    public User read(int id) throws DAOException {
        try {
            Session session = HibernateConnection.getInstance().getSession();
            return session.find(User.class, id);
        } catch (Exception e) {
            throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
        }
    }

    @Override
    public List<User> list() throws DAOException {
        try {
            Session session = HibernateConnection.getInstance().getSession();
            return session.createQuery("select u " + " from users u", User.class).getResultList();
        } catch (Exception e) {
            throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
        }
    }

    @Override
    public void update(User entity) throws DAOException {
        try {
            Session session = HibernateConnection.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();

        } catch (Exception e) {
            throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try {
            Session session = HibernateConnection.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
            User user = read(id);
            if (user != null) {
                session.remove(user);
            }

            transaction.commit();
        } catch (Exception e) {
            throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
        }
    }
}