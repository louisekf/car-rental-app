package com.autorental.controllers;

import com.autorental.model.Chauffeur;
import com.autorental.runtime.Testeur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ModifierChauffeur {

    @FXML private TextField LastNameField;
    @FXML private TextField FirstNameField;
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
    private void handleSave() {
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
            closeWindow();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) LastNameField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
