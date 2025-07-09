package com.autorental.dao.impl;

import com.autorental.model.Vehicule;

public class HibernateVehiculeDaoImpl extends HibernateObjectDaoImpl<Vehicule>{
    public HibernateVehiculeDaoImpl() {
        super(Vehicule.class);
    }
}
