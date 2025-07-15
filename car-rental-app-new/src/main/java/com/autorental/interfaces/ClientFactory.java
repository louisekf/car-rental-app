package com.autorental.interfaces;

import com.autorental.dao.IDao;
import com.autorental.model.Client;

public interface ClientFactory {
    IDao<Client> getClientDao(Class<? extends IDao<Client>> daoClient);
}
