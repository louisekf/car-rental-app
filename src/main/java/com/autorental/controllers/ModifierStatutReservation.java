package com.autorental.controllers;

import com.autorental.dao.impl.HibernateReservationDaoImpl;
import com.autorental.model.Reservation;
import com.autorental.runtime.Testeur;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Date;

public class ModifierStatutReservation {

    @FXML
    private TextField clientField;
    @FXML
    private TextField vehiculeField;
    @FXML
    private TextField chauffeurField;
    @FXML
    private DatePicker dateRetraitField;
    @FXML
    private DatePicker dateRetourField;
    @FXML
    private ComboBox<String> statutField;


    private Reservation reservation;
    Testeur testeur = new Testeur();

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;

        chauffeurField.setText(reservation.getChauffeur() != null ? reservation.getChauffeur().toString() : "");
        clientField.setText(reservation.getClient() != null ? reservation.getClient().toString() : "");
        vehiculeField.setText(reservation.getVehicule() != null ? reservation.getVehicule().toString() : "");
        if (reservation.getDate_retrait() != null)
            dateRetraitField.setValue(new Date(reservation.getDate_retrait().getTime()).toLocalDate());
        if (reservation.getDate_retour() != null)
            dateRetourField.setValue(new Date(reservation.getDate_retour().getTime()).toLocalDate());
        statutField.getItems().setAll("En cours", "Terminée", "Enregistrée");
        statutField.setValue(reservation.getStatut());

        // Désactivation des champs sauf statut
        chauffeurField.setDisable(true);
        clientField.setDisable(true);
        vehiculeField.setDisable(true);
        dateRetraitField.setDisable(true);
        dateRetourField.setDisable(true);
    }


    @FXML
    private void handleSave() {
        String nouveauStatut = statutField.getValue();
        if (nouveauStatut != null && !nouveauStatut.equals(reservation.getStatut())) {
            reservation.setStatut(nouveauStatut);
            try {
                testeur.updateObject(reservation, Reservation.class);
                showAlert("Succès", "Statut mis à jour !");
            } catch (Exception e) {
                showAlert("Erreur", "Impossible de mettre à jour : " + e.getMessage());
            }
        }
        closeWindow();
    }


    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) chauffeurField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}