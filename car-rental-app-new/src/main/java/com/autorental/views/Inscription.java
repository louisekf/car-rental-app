package com.autorental.views;

import com.autorental.dao.impl.HibernateClientDaoImpl;
import com.autorental.dao.impl.HibernateUserDaoImpl;
import com.autorental.model.Client;
import com.autorental.model.User;
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


    private final HibernateClientDaoImpl clientDao = new HibernateClientDaoImpl();
    private final HibernateUserDaoImpl userDao = new HibernateUserDaoImpl();

    @FXML
    private void onInscrire(ActionEvent event) {
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String email = emailField.getText().trim();
        String tel = telField.getText().trim();
        String adresse = adresseField.getText().trim();
        String password = passwordField.getText();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs obligatoires.");
            return;
        }


        if (userDao.loginExists(email)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Cet email existe déjà .");
            return;
        }


        Client client = new Client();
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setEmail(email);
        client.setTel(tel);
        client.setAdresse(adresse);
       // clientDao.createClient(client);

        User user = new User();
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setLogin(email);
        user.setPassword(password);
        user.setRole("CLIENT");

        userDao.createUser(user);

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
