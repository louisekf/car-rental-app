package com.autorental.dao.impl;

import com.autorental.db.HibernateConnection;
import com.autorental.model.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class HibernateUserDaoImpl extends HibernateObjectDaoImpl<User>{
    public HibernateUserDaoImpl() {
        super(User.class);
    }

    //Quand vous créer une opération, mettez openSession() au lieu de getSession
    public boolean loginExists(String login) {
        try (Session session = HibernateConnection.getInstance().openSession()) {
            Long count = session.createQuery(
                            "SELECT COUNT(u) FROM User u WHERE u.login = :login", Long.class)
                    .setParameter("login", login)
                    .uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User checkLogin(String loginOrEmail, String password) {
        try (Session session = HibernateConnection.getInstance().openSession()) {
            String hql = "FROM User u WHERE u.login = :loginOrEmail " +
                    "AND u.password = :password";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("loginOrEmail", loginOrEmail);
            query.setParameter("password", password);
            return query.uniqueResult();
        } catch (Exception e) {
            System.err.println("Erreur lors de la vérification du login : " + e.getMessage());
            return null;
        }
    }


    public void createUser(User user) {

    }
}