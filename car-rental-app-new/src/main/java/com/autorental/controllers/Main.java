package com.autorental.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Main implements Initializable {
    @FXML
    private Label pageTitleLabel;

    @FXML
    private AnchorPane contentPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPage("Accueil.fxml");
    }

    public void loadAccueilPage() {
        loadPage("Accueil.fxml");
    }

    public void loadReservationsPage() {
        loadPage("MesReservations.fxml");
    }

    public void loadFormulairePage() {
        loadPage("Formulaire.fxml");
    }

    public void loadSettingsPage() {
        loadPage("Parametres.fxml");
    }

    public void setPageTitle(String title) {
        pageTitleLabel.setText(title);
    }

    private void loadPage(String fxmlFile) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/" + fxmlFile));
            contentPane.getChildren().setAll(pane);
            switch (fxmlFile) {
                case "Accueil.fxml" -> setPageTitle("Accueil");
                case "MesReservations.fxml" -> setPageTitle("Mes réservations");
                case "Formulaire.fxml" -> setPageTitle("Formulaire");
                case "Parametres.fxml" -> setPageTitle("Paramètres");
                default -> setPageTitle("Page");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleDeconnexion(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Authentification.fxml"));
            Scene loginScene = new Scene(loader.load());
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(loginScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
