package com.autorental.dao.impl;

import com.autorental.db.HibernateConnection;
import com.autorental.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateUserDaoImpl {

    public boolean loginExists(String login) {
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(u) FROM User u WHERE u.login = :login", Long.class);
            query.setParameter("login", login);
            Long count = query.uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public User checkLogin(String loginOrEmail, String password) {
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            String recherche = "FROM User u WHERE (u.login = :loginOrEmail) AND u.password = :password";
            Query<User> query = session.createQuery(recherche, User.class);
            query.setParameter("loginOrEmail", loginOrEmail);
            query.setParameter("password", password);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<User> listAll() {
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            return session.createQuery("FROM User", User.class).getResultList();
        }
    }
}
