package com.autorental.controllers;

import com.autorental.model.Client;
import com.autorental.model.User;
import com.autorental.runtime.Testeur;
import com.autorental.utils.Session;
import com.autorental.dao.impl.HibernateClientDaoImpl;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Parametres implements Initializable {
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;
    @FXML private TextField telField;
    @FXML private TextField adresseField;
    @FXML private TextField oldPasswordField;
    @FXML private TextField newPasswordField;
    @FXML private Button saveButton;

    private Client client;
    private User user;
    HibernateClientDaoImpl clientDao = new HibernateClientDaoImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = Session.getCurrentUser();

        if (user != null && user.getRole().equalsIgnoreCase("CLIENT")) {
            client = clientDao.getClientByUserLogin(user.getLogin());
            if (client != null) {
                nomField.setText(client.getNom());
                prenomField.setText(client.getPrenom());
                emailField.setText(client.getEmail());
                telField.setText(client.getTel());
                adresseField.setText(client.getAdresse());
            }
        }

        saveButton.setOnAction(e -> saveChanges());
    }

    private void saveChanges() {
        Testeur testeur = new Testeur();

        if (user == null || client == null) return;

        client.setNom(nomField.getText());
        client.setPrenom(prenomField.getText());
        client.setEmail(emailField.getText());
        client.setTel(telField.getText());
        client.setAdresse(adresseField.getText());

        try {
            testeur.updateObject(client, Client.class);
        } catch (Exception e) {
            showAlert("Erreur", "Impossible de mettre à jour les données client.");
            return;
        }

        String oldPwd = oldPasswordField.getText();
        String newPwd = newPasswordField.getText();

        if (!oldPwd.isEmpty() && !newPwd.isEmpty()) {
            if (user.getPassword().equals(oldPwd)) {
                user.setPassword(newPwd);
                try {
                    testeur.updateObject(user, User.class);
                } catch (Exception e) {
                    showAlert("Erreur", "Échec de mise à jour du mot de passe.");
                    return;
                }
            } else {
                showAlert("Erreur", "Ancien mot de passe incorrect.");
                return;
            }
        }

        showAlert("Succès", "Modifications enregistrées avec succès !");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
