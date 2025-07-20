package com.autorental.controllers;

import com.autorental.dao.impl.HibernateVehiculeDaoImpl;
import com.autorental.exceptions.DAOException;
import com.autorental.runtime.Testeur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AjoutVehicule {
    @FXML
    private AnchorPane mainContentPane;

    @FXML private TextField brandField;
    @FXML private TextField modelField;
    @FXML private TextField licensePlateField;
    @FXML private TextField tarifField;
    @FXML private ComboBox<String>  availabilityChoice;

    @FXML
    public void initialize() {
        availabilityChoice.getItems().addAll("Vrai", "Faux");
    }

    @FXML
    private void onEnregistrer(ActionEvent event) throws DAOException {
        String marque = brandField.getText().trim();
        String modele = modelField.getText().trim();
        String immatriculation = licensePlateField.getText().trim();
        double tarif = Double.parseDouble(tarifField.getText().trim());
        boolean dispo = availabilityChoice.getValue().equalsIgnoreCase("Vrai");

        if (marque.isEmpty() || modele.isEmpty() || immatriculation.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs obligatoires.");
            return;
        }

        Testeur testeur = new Testeur();
        testeur.ajouterVehicule(marque, modele, tarif, dispo, immatriculation);

        showAlert(Alert.AlertType.INFORMATION, "Succès", "Vehicule ajouté avec succès !");
        onBackToGestionVehicules(event);
    }

    @FXML
    private void onAnnuler(ActionEvent actionEvent) {
        brandField.clear();
        modelField.clear();
        licensePlateField.clear();
        tarifField.clear();
        availabilityChoice.getSelectionModel().clearSelection();
        onBackToGestionVehicules(actionEvent);
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

    private void showAlert(Alert.AlertType type, String titre, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
