package com.autorental.controllers;

import com.autorental.model.Client;
import com.autorental.model.User;
import com.autorental.utils.Session;
import com.autorental.dao.impl.HibernateClientDaoImpl;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Profile implements Initializable {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;
    @FXML private TextField telField;
    @FXML private TextField adresseField;
    @FXML private PasswordField passwordField;
    @FXML private TextField pointsField;
    @FXML private TextField walletField;

    private final HibernateClientDaoImpl clientDao = new HibernateClientDaoImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = Session.getCurrentUser();

        if (user != null && user.getRole().equalsIgnoreCase("CLIENT")) {
            Client client = clientDao.getClientByUserLogin(user.getLogin());
            if (client != null) {
                nomField.setText(client.getNom());
                prenomField.setText(client.getPrenom());
                emailField.setText(client.getEmail());
                telField.setText(client.getTel());
                adresseField.setText(client.getAdresse());
                pointsField.setText(String.valueOf(client.getPts_fidelite()));
                walletField.setText(String.valueOf(client.getSolde()));
                passwordField.setText(user.getPassword());
            }
        }
    }
}
