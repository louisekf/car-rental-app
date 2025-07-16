package com.autorental.controllers;

import com.autorental.dao.impl.HibernateClientDaoImpl;
import com.autorental.model.*;
import com.autorental.runtime.Testeur;
import com.autorental.exceptions.DAOException;
import com.autorental.utils.Session;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Formulaire {

    @FXML private ComboBox<Vehicule> vehiculeComboBox;
    @FXML private ComboBox<String> typeReservationComboBox;
    @FXML private ComboBox<Chauffeur> chauffeurComboBox;
    @FXML private DatePicker dateRetraitPicker;
    @FXML private DatePicker dateRetourPicker;

    private Reservation reservationTemp;

    Testeur testeur = new Testeur();

    private Main mainController;

    public void setMainController(Main mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        selectTypeReservation();
        loadVehicules();
        loadChauffeurs();
        setupTypeReservationLogic();
    }

    private void selectTypeReservation() {
        typeReservationComboBox.setItems(FXCollections.observableArrayList("Avec chauffeur", "Sans chauffeur"));
    }

    private void loadVehicules() {
        try {
            List<Vehicule> vehicules = testeur.listerObjects(Vehicule.class)
                    .stream()
                    .filter(Vehicule::isDispo)
                    .toList();

            vehiculeComboBox.setItems(FXCollections.observableArrayList(vehicules));
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    private void loadChauffeurs() {
        try {
            List<Chauffeur> chauffeurs = testeur.listerObjects(Chauffeur.class)
                    .stream().filter(Chauffeur::getDispo).toList();
            chauffeurComboBox.setItems(FXCollections.observableArrayList(chauffeurs));
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    private void setupTypeReservationLogic() {
        typeReservationComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            boolean withDriver = "Avec chauffeur".equals(newVal);
            chauffeurComboBox.setDisable(!withDriver);
        });
    }

    @FXML
    private void onSuivantClicked() {
        try {
            Vehicule vehicule = vehiculeComboBox.getValue();
            String type = typeReservationComboBox.getValue();
            Chauffeur chauffeur = chauffeurComboBox.getValue();
            LocalDate dateRetrait = dateRetraitPicker.getValue();
            LocalDate dateRetour = dateRetourPicker.getValue();

            if (vehicule == null || dateRetrait == null || dateRetour == null || type == null) {
                showAlert("Veuillez remplir tous les champs requis.");
                return;
            }

            String login = Session.getCurrentUser().getLogin();
            HibernateClientDaoImpl clientDao = new HibernateClientDaoImpl();
            Client client = clientDao.getClientByUserLogin(login);
            if (client == null) {
                showAlert("Impossible de retrouver le client connecté.");
                return;
            }

            reservationTemp = new Reservation();
            reservationTemp.setClient(client);
            reservationTemp.setVehicule(vehicule);
            reservationTemp.setDate_retrait(java.sql.Date.valueOf(dateRetrait));
            reservationTemp.setDate_retour(java.sql.Date.valueOf(dateRetour));
            reservationTemp.setStatut(type);

            if ("Avec chauffeur".equals(type)) {
                if (chauffeur == null) {
                    showAlert("Veuillez choisir un chauffeur.");
                    return;
                }
                reservationTemp.setChauffeur(chauffeur);
            }
            openFacturePage();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openFacturePage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/FactureClient.fxml"));
        AnchorPane facturePane = loader.load();

        FactureClient controller = loader.getController();
        controller.setReservation(reservationTemp);
        controller.setMainController(mainController);

        mainController.getContentPane().getChildren().setAll(facturePane);
        mainController.setPageTitle("Facture");
    }


    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Champ manquant");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
