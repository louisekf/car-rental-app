package com.autorental.controllers;

import com.autorental.dao.impl.HibernateChauffeurDaoImpl;
import com.autorental.model.Chauffeur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AjoutChauffeur {

    @FXML private TextField LastNameField;
    @FXML private TextField FirstNameField;
    @FXML private ComboBox<String> availabilityChoice;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private final HibernateChauffeurDaoImpl chauffeurDao = new HibernateChauffeurDaoImpl();

    @FXML
    public void initialize() {
        availabilityChoice.getItems().addAll("Disponible", "Indisponible");
    }

    @FXML
    private void handleSave(ActionEvent event) {
        String nom = LastNameField.getText();
        String prenom = FirstNameField.getText();
        String dispo = availabilityChoice.getValue();

        if (nom.isEmpty() || prenom.isEmpty() || dispo == null) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }

        Chauffeur chauffeur = new Chauffeur(nom, prenom, dispo);
        chauffeurDao.save(chauffeur);
        showAlert("Chauffeur ajouté avec succès !");
        clearFields();
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        clearFields();
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
