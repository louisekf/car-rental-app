package com.autorental.controllers;

import com.autorental.dao.impl.HibernateChauffeurDaoImpl;
import com.autorental.exceptions.DAOException;
import com.autorental.model.Chauffeur;
import com.autorental.runtime.Testeur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AjoutChauffeur {

    @FXML
    private AnchorPane mainContentPane;

    @FXML private TextField LastNameField;
    @FXML private TextField FirstNameField;
    @FXML private ComboBox<String> availabilityChoice;

    //private final HibernateChauffeurDaoImpl chauffeurDao = new HibernateChauffeurDaoImpl();
    private Chauffeur chauffeur;
    Testeur testeur = new Testeur();

    @FXML
    public void initialize() {
        availabilityChoice.getItems().addAll("Disponible", "Indisponible");
    }

    @FXML
    private void handleSave(ActionEvent event) throws DAOException {
        String nom = LastNameField.getText();
        String prenom = FirstNameField.getText();
        String selectedDispo = availabilityChoice.getValue();

        if (nom.isEmpty() || prenom.isEmpty() || selectedDispo == null) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }
        boolean dispo = selectedDispo.equals("Disponible");

        chauffeur = new Chauffeur(nom, prenom, dispo);
        testeur.ajouterObject(chauffeur,Chauffeur.class);
        showAlert("Chauffeur ajouté avec succès !");
        clearFields();
        onBackToGestionVehicules(event);
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        clearFields();
        onBackToGestionVehicules(event);
    }
    @FXML
    private void onBackToGestionVehicules(ActionEvent event){
        if (mainContentPane == null) {
            System.err.println("Erreur : mainContentPane est null !");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GestionChauffeur.fxml"));
            AnchorPane gestionVehiculePane = loader.load();

            mainContentPane.getChildren().setAll(gestionVehiculePane);
            AnchorPane.setTopAnchor(gestionVehiculePane, 0.0);
            AnchorPane.setBottomAnchor(gestionVehiculePane, 0.0);
            AnchorPane.setLeftAnchor(gestionVehiculePane, 0.0);
            AnchorPane.setRightAnchor(gestionVehiculePane, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        LastNameField.clear();
        FirstNameField.clear();
        availabilityChoice.getSelectionModel().clearSelection();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}