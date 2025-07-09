package com.autorental.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainAdmin implements Initializable {

    @FXML
    private Label pageTitleLabel;

    @FXML
    private AnchorPane contentPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {loadPage("Dashboard_Admin.fxml");}

    @FXML
    public void loadDashboardPage() {loadPage("Dashboard_Admin.fxml");}

    @FXML
    public void loadGestionVehiculesPage() {loadPage("GestionVehicule.fxml");}

    @FXML
    public void loadGestionChauffeursPage() {loadPage("GestionChauffeur.fxml");}

    @FXML
    public void loadGestionReservationsPage() {loadPage("GestionReservation.fxml");}

    public void setPageTitle(String title) {
        pageTitleLabel.setText(title);
    }

    private void loadPage(String fxmlFile) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/" + fxmlFile));
            contentPane.getChildren().setAll(pane);
            switch (fxmlFile) {
                case "Dashboard_Admin.fxml" -> setPageTitle("Tableau de bord");
                case "GestionVehicule.fxml" -> setPageTitle("Gestion des véhicules");
                case "GestionChauffeur.fxml" -> setPageTitle("Gestion des chauffeurs");
                case "GestionReservation.fxml" -> setPageTitle("Gestion des réservations");
                default -> pageTitleLabel.setText("Page");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
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
