package com.autorental.dao.impl;

import com.autorental.dao.IDao;
import com.autorental.db.HibernateConnection;
import com.autorental.exceptions.DAOException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateObjectDaoImpl<T> implements IDao<T> {

    private final Class<T> type;

    public HibernateObjectDaoImpl(Class<T> type) {
        this.type = type;
    }

    @Override
    public void create(T entity) throws DAOException {
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
        } catch (Exception e) {
            throw new DAOException("Erreur create: " + e.getMessage());
        }
    }

    @Override
    public T read(int id) throws DAOException {
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            return session.find(type, id);
        } catch (Exception e) {
            throw new DAOException("Erreur read: " + e.getMessage());
        }
    }

    @Override
    public List<T> list() throws DAOException {
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            return session.createQuery("FROM " + type.getSimpleName(), type).list();
        } catch (Exception e) {
            throw new DAOException("Erreur list: " + e.getMessage());
        }
    }

    @Override
    public void update(T entity) throws DAOException {
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(entity);
            tx.commit();
        } catch (Exception e) {
            throw new DAOException("Erreur update: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try (Session session = HibernateConnection.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            T entity = session.find(type, id);
            if (entity != null) {
                session.remove(entity);
            }
            tx.commit();
        } catch (Exception e) {
            throw new DAOException("Erreur delete: " + e.getMessage());
        }
    }
}
