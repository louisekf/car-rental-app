package com.autorental.dao.impl;

import com.autorental.db.HibernateConnection;
import com.autorental.exceptions.DAOException;
import com.autorental.model.Chauffeur;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateChauffeurDaoImpl extends HibernateObjectDaoImpl<Chauffeur>{
    public HibernateChauffeurDaoImpl() {
        super(Chauffeur.class);
    }


        public void save(Chauffeur chauffeur) {
            Transaction transaction = null;
            try (Session session = HibernateConnection.getInstance().openSession()) {
                transaction = session.beginTransaction();
                session.persist(chauffeur);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
            }
        }

        public List<Chauffeur> findAll() {
            try (Session session = HibernateConnection.getInstance().openSession()) {
                return session.createQuery("from Chauffeur", Chauffeur.class).list();
            }
        }

        public void update(Chauffeur chauffeur) {
            Transaction transaction = null;
            try (Session session = HibernateConnection.getInstance().openSession()) {
                transaction = session.beginTransaction();
                session.merge(chauffeur);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
            }
        }

        public void delete(Chauffeur chauffeur) {
            Transaction transaction = null;
            try (Session session = HibernateConnection.getInstance().openSession()) {
                transaction = session.beginTransaction();
                session.remove(session.contains(chauffeur) ? chauffeur : session.merge(chauffeur));
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
            }
        }

        public Chauffeur findById(int id) {
            try (Session session = HibernateConnection.getInstance().openSession()) {
                return session.get(Chauffeur.class, id);
            }
        }
    }
