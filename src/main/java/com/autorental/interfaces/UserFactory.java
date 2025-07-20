package com.autorental.interfaces;

import com.autorental.dao.IDao;
import com.autorental.model.User;

public interface UserFactory {
    IDao<User> getUserDao(Class<? extends IDao<User>> daoUser);
}
