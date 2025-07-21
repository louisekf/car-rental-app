package com.autorental.controllers;

import com.autorental.model.Chauffeur;
import com.autorental.runtime.Testeur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifierChauffeur {

    @FXML
    private AnchorPane mainContentPane;

    @FXML private TextField LastNameField;
    @FXML private TextField FirstNameField;
    @FXML private TextField phoneField;
    @FXML private ComboBox<String> availabilityChoice;

    Testeur testeur = new Testeur();
    private Chauffeur chauffeur;

    @FXML
    public void initialize() {
        availabilityChoice.getItems().addAll("Disponible", "Indisponible");
    }

    public void setChauffeur(Chauffeur chauffeur) {
        this.chauffeur = chauffeur;
        LastNameField.setText(chauffeur.getNom());
        FirstNameField.setText(chauffeur.getPrenom());
        availabilityChoice.setValue(chauffeur.getDispo() ? "Disponible" : "Indisponible");
    }

    @FXML
    private void handleSave(ActionEvent event) {
        try {
            chauffeur.setNom(LastNameField.getText());
            chauffeur.setPrenom(FirstNameField.getText());

            String selected = availabilityChoice.getValue();
            if (selected == null) {
                showAlert("Veuillez sélectionner une disponibilité.");
                return;
            }
            boolean isAvailable = "Disponible".equals(selected);
            chauffeur.setDispo(isAvailable);

            testeur.updateObject(chauffeur, Chauffeur.class);
            showAlert("Chauffeur modifié avec succès !");
            onBackToGestionVehicules(event);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
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

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}