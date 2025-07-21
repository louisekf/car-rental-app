package com.autorental.controllers;

import com.autorental.dao.impl.HibernateClientDaoImpl;
import com.autorental.dao.impl.HibernateUserDaoImpl;
import com.autorental.exceptions.DAOException;
import com.autorental.runtime.Testeur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Inscription {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;
    @FXML private TextField telField;
    @FXML private TextField adresseField;
    @FXML private PasswordField passwordField;

    @FXML
    private void onInscrire(ActionEvent event) throws DAOException {
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String email = emailField.getText().trim();
        String tel = telField.getText().trim();
        String adresse = adresseField.getText().trim();
        String password = passwordField.getText();

        HibernateUserDaoImpl userDao = new HibernateUserDaoImpl();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs obligatoires.");
            return;
        }
        if (userDao.loginExists(email)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Cet email existe déjà .");
            return;
        }

        Testeur testeur = new Testeur();
        testeur.inscriptionClient(nom, prenom, email, tel, adresse);
        testeur.inscriptionUser(prenom, nom, "CLIENT", email, password);

        showAlert(Alert.AlertType.INFORMATION, "Succès", "Inscription réussie !");
        backToLogin(event);
    }

    @FXML
    private void backToLogin(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/views/Authentification.fxml"));
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String titre, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
