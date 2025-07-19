package com.autorental.controllers;

import com.autorental.exceptions.DAOException;
import com.autorental.model.Vehicule;
import com.autorental.runtime.Testeur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ModifierVehicule {
    @FXML
    private AnchorPane mainContentPane;

    @FXML
    private TextField brandField;
    @FXML private TextField modelField;
    @FXML private TextField licensePlateField;
    @FXML private TextField tarifField;
    @FXML private ComboBox<String> availabilityChoice;

    @FXML
    public void initialize() {
        availabilityChoice.getItems().addAll("Vrai", "Faux");
    }

    Vehicule vehicule;

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
        brandField.setText(vehicule.getMarque());
        modelField.setText(vehicule.getModele());
        licensePlateField.setText(vehicule.getImmatriculation());
        tarifField.setText(String.valueOf(vehicule.getTarif()));
        availabilityChoice.setValue(vehicule.isDispo() ? "Vrai" : "Faux");
    }

    @FXML
    private void onEnregistrer(ActionEvent event) throws DAOException {
        vehicule.setMarque(brandField.getText());
        vehicule.setModele(modelField.getText());
        vehicule.setImmatriculation(licensePlateField.getText());
        vehicule.setTarif(Double.parseDouble(tarifField.getText()));
        vehicule.setDispo(availabilityChoice.getValue().equalsIgnoreCase("Vrai"));

        Testeur.updateObject(vehicule, Vehicule.class);
        showAlert("Vehicule modifié avec succès !");
        onBackToGestionVehicules(event);
    }

    @FXML
    private void onAnnuler(ActionEvent event) {
        onBackToGestionVehicules(event);
    }

    @FXML
    private void onBackToGestionVehicules(ActionEvent event){
        if (mainContentPane == null) {
            System.err.println("Erreur : mainContentPane est null !");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GestionVehicule.fxml"));
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
