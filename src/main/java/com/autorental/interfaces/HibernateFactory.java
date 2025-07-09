package com.autorental.interfaces;

import com.autorental.dao.impl.HibernateObjectDaoImpl;
import com.autorental.exceptions.DAOException;

public interface HibernateFactory {
    <T> HibernateObjectDaoImpl<T> getHibernateObjectDaoImpl(Class<T> entityClass) throws DAOException;
}
