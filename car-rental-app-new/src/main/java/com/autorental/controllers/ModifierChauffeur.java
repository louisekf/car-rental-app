package com.autorental.controllers;

import com.autorental.dao.impl.HibernateChauffeurDaoImpl;
import com.autorental.model.Chauffeur;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ModifierChauffeur {

    @FXML private TextField LastNameField;
    @FXML private TextField FirstNameField;
    @FXML private TextField PhoneField;
    @FXML private ComboBox<String> availabilityChoice;

    private Chauffeur chauffeur;
    private final HibernateChauffeurDaoImpl chauffeurDao = new HibernateChauffeurDaoImpl();

    @FXML
    public void initialize() {
        availabilityChoice.getItems().addAll("Disponible", "Indisponible");
    }

    public void setChauffeur(Chauffeur chauffeur) {
        this.chauffeur = chauffeur;
        LastNameField.setText(chauffeur.getNom());
        FirstNameField.setText(chauffeur.getPrenom());
        availabilityChoice.setValue(chauffeur.getDispo());
    }

    @FXML
    private void handleSave() {
        chauffeur.setNom(LastNameField.getText());
        chauffeur.setPrenom(FirstNameField.getText());
        chauffeur.setDispo(availabilityChoice.getValue());

        chauffeurDao.update(chauffeur);
        showAlert("Chauffeur modifié avec succès !");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
