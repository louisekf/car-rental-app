package com.autorental.controllers;

import com.autorental.dao.impl.HibernateReservationDaoImpl;
import com.autorental.model.Reservation;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ModifierStatutReservationController {

    @FXML private TextField clientField;
    @FXML private TextField vehiculeField;
    @FXML private TextField chauffeurField;
    @FXML private DatePicker dateRetraitField;
    @FXML private DatePicker dateRetourField;
    @FXML private ComboBox<String> statutField;


    private Reservation reservation;
    private final HibernateReservationDaoImpl reservationDao = new HibernateReservationDaoImpl();

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;

        chauffeurField.setText(reservation.getChauffeur() != null ? reservation.getChauffeur().getNomComplet() : "");
        clientField.setText(reservation.getClient() != null ? reservation.getClient().getNomComplet() : "");
        vehiculeField.setText(reservation.getVehicule() != null ? reservation.getVehicule().getMarqueModele() : "");


        // Conversion LocalDateTime -> LocalDate pour DatePicker
        dateRetraitField.setValue(reservation.getDate_retrait() != null ?
                reservation.getDate_retrait().toLocalDate() : null);
        dateRetourField.setValue(reservation.getDate_retour() != null ?
                reservation.getDate_retour().toLocalDate() : null);

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
            reservationDao.update(reservation);
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
}
