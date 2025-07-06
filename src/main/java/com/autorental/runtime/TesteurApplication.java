package com.autorental.runtime;

import com.autorental.dao.impl.HibernateUserDaoImpl;
import com.autorental.exceptions.DAOException;
import com.autorental.model.User;

import java.util.List;

public class TesteurApplication {
    public static void main(String[] args) {

        HibernateUserDaoImpl userDao = new HibernateUserDaoImpl();

        try {
            //Create a user
            User newUser = new User("Mariama", "Faye", "Admin", "mariama@example.com", "1234");
            userDao.create(newUser);
            System.out.println("User created!");

            //List all users
            List<User> users = userDao.list();
            users.forEach(u -> System.out.println(u.getPrenom() + " " + u.getNom()));

            //Read a user by ID
            User user = userDao.read(1);
            if (user != null) {
                System.out.println("Found: " + user.getLogin());
            }

            //Update a user
            user.setPassword("newpassword123");
            userDao.update(user);
            System.out.println("Updated user password.");
//
//            //Delete user
//            userDao.delete(user.getId());
//            System.out.println("User deleted.");

        } catch (DAOException e) {
            System.err.println("Error" + e.getMessage());
        }
    }
}
