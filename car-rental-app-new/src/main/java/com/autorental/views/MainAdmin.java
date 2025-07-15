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

public class MainAdmin implements Initializable {

    @FXML
    private AnchorPane contentPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPage("Dashboard_Admin.fxml");
    }

    @FXML
    public void loadDashboardPage() {
        loadPage("Dashboard_Admin.fxml");
    }

    @FXML
    public void loadGestionVehiculesPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GestionVehicule.fxml"));
            AnchorPane gestionPane = loader.load();

            GestionVehicules controller = loader.getController();
            controller.setMainContentPane(contentPane);

            contentPane.getChildren().setAll(gestionPane);
            setAnchorPaneFullSize(gestionPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadGestionChauffeursPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GestionChauffeur.fxml"));
            AnchorPane gestionPane = loader.load();

            GestionChauffeur controller = loader.getController();
            controller.setMainContentPane(contentPane);

            contentPane.getChildren().setAll(gestionPane);
            setAnchorPaneFullSize(gestionPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadGestionReservationsPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GestionReservation.fxml"));
            AnchorPane gestionPane = loader.load();

            GestionReservations controller = loader.getController();
            controller.setMainContentPane(contentPane);

            contentPane.getChildren().setAll(gestionPane);
            setAnchorPaneFullSize(gestionPane);
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

    @FXML
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

}
