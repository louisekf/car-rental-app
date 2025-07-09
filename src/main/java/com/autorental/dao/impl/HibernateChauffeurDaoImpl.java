package com.autorental.dao.impl;

import com.autorental.model.Chauffeur;

public class HibernateChauffeurDaoImpl extends HibernateObjectDaoImpl<Chauffeur>{
    public HibernateChauffeurDaoImpl() {
        super(Chauffeur.class);
    }
}
