package com.autorental.runtime;

import com.autorental.dao.impl.HibernateObjectDaoImpl;
import com.autorental.exceptions.DAOException;
import com.autorental.factory.ConcreteFactory;
import com.autorental.interfaces.HibernateFactory;
import com.autorental.model.Client;
import com.autorental.model.User;

public class Testeur {

    public Testeur(){

    }

    public static <T> void ajouterObject(T entity, Class<T> entityClass) throws DAOException {
        HibernateObjectDaoImpl<T> hibernateDao = ConcreteFactory
                .getFactory(HibernateFactory.class)
                .getHibernateObjectDaoImpl(entityClass);
        hibernateDao.create(entity);
    }

    public static <T> void deleteObject(int objectId, Class<T> entityClass) throws DAOException {
        HibernateObjectDaoImpl<T> hibernateDao = ConcreteFactory
                .getFactory(HibernateFactory.class)
                .getHibernateObjectDaoImpl(entityClass);
        hibernateDao.delete(objectId);
    }

    public static <T> T rechercherObject(int objectId, Class<T> entityClass) throws DAOException {
        HibernateObjectDaoImpl<T> hibernateDao = ConcreteFactory
                .getFactory(HibernateFactory.class)
                .getHibernateObjectDaoImpl(entityClass);
        return hibernateDao.read(objectId);
    }

    public static <T> void updateObject(T entity, Class<T> entityClass) throws DAOException {
        HibernateObjectDaoImpl<T> hibernateDao = ConcreteFactory
                .getFactory(HibernateFactory.class)
                .getHibernateObjectDaoImpl(entityClass);
        hibernateDao.update(entity);
    }


    public static void inscriptionClient(String nom, String prenom, String email, String tel, String adresse) throws DAOException {
        Client client = new Client(nom, prenom, email, tel, adresse);
        ajouterObject(client, Client.class);
    }

    public static void inscriptionUser(String prenom, String nom, String role, String login, String password) throws DAOException {
        User user = new User(prenom, nom, role, login, password);
        ajouterObject(user, User.class);
    }

}
