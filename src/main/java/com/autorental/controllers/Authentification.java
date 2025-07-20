package com.autorental.controllers;

import com.autorental.dao.impl.HibernateUserDaoImpl;
import com.autorental.model.User;
import com.autorental.utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Map;


public class Authentification {
    Map<String, String> roleToFxml = Map.of(
            "ADMIN", "/views/MainAdmin.fxml",
            "CLIENT", "/views/Main.fxml"
    );
    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    private final HibernateUserDaoImpl userDao = new HibernateUserDaoImpl();

    private void openPage(String fxmlPath, ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void onLogin(ActionEvent event) {
        String loginOrEmail = loginField.getText().trim();
        String password = passwordField.getText().trim();

        if (loginOrEmail.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }


        User user = userDao.checkLogin(loginOrEmail, password);
        if (user != null) {
            Session.setCurrentUser(user);
            String fxmlPath = roleToFxml.get(user.getRole().toUpperCase());
            if (fxmlPath != null) {
                try {
                    openPage(fxmlPath, event);
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ouvrir la page.");
                }

            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Login ou mot de passe incorrect.");
        }
    }

    @FXML
    private void goToInscription(ActionEvent event) {
        try {
            Stage stage = (Stage) loginField.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/views/Inscription.fxml"));
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d’ouvrir la page d’inscription.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void onCancel(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
