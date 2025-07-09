package com.autorental.dao.impl;

import com.autorental.model.Facture;

public class HibernateFactureDaoImpl extends HibernateObjectDaoImpl<Facture>{
    public HibernateFactureDaoImpl() {
        super(Facture.class);
    }
}
