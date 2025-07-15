package com.autorental.dao.impl;

import com.autorental.model.Client;

public class HibernateClientDaoImpl extends HibernateObjectDaoImpl<Client>{
    public HibernateClientDaoImpl() {
        super(Client.class);
    }
}
