package com.autorental.dao.impl;

import com.autorental.db.HibernateConnection;
import com.autorental.exceptions.DAOException;
import com.autorental.model.Facture;
import org.hibernate.Session;

import java.util.List;

public class HibernateFactureDaoImpl extends HibernateObjectDaoImpl<Facture> {
    public HibernateFactureDaoImpl() {
        super(Facture.class);
    }

    public List<Object[]> getRevenusParDateRetrait() throws DAOException {
        try (Session session = HibernateConnection.getInstance().openSession()) {
            return session.createQuery("""
                    SELECT 
                        r.date_retrait, 
                        f.montant
                    FROM Facture f
                    JOIN f.reservation r
                    """, Object[].class).list();
        } catch (Exception e) {
            throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
        }
    }
}
