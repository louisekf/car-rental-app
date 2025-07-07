package com.autorental.views;

import javafx.event.ActionEvent;
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
    private AnchorPane contentPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadPage("Accueil.fxml");
    }

    @FXML
    public void loadAccueilPage() {
        loadPage("Accueil.fxml");
    }

    @FXML
    public void loadReservationsPage() {
        loadPage("MesReservations.fxml");
    }

    @FXML
    public void loadFormulairePage() {
        loadPage("Formulaire.fxml");
    }

    @FXML
    public void loadSettingsPage() {
        loadPage("Parametres.fxml");
    }
    public void handleDeconnexion() {
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
    private void loadPage(String fxmlFile) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/" + fxmlFile));
            contentPane.getChildren().setAll(pane);
            setAnchorPaneFullSize(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setAnchorPaneFullSize(AnchorPane pane) {
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
    }

}
