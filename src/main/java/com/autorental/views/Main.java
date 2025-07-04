package com.autorental.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Main implements Initializable {

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

    private void loadPage(String fxmlFile) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/example/interfaces/" + fxmlFile));
            contentPane.getChildren().setAll(pane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
