package com.autorental.controllers;

import com.autorental.exceptions.DAOException;
import com.autorental.model.Client;
import com.autorental.model.Facture;
import com.autorental.model.Reservation;

import com.autorental.runtime.Testeur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class FactureClient {

    @FXML private TextField tarifField;
    @FXML private TextField periodeField;
    @FXML private TextField montantField;
    @FXML private Label pointsLabel;
    @FXML private ChoiceBox<String> reductionChoice;


    Testeur testeur = new Testeur();
    private double montantTotal;
    private String selectedReduction = "Aucune réduction";
    private Reservation reservation;
    private Client client;
    private Main mainController;

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
        populateFields();
    }

    private void populateFields() {
        try {
            double tarif = reservation.getVehicule().getTarif();
            Date start = reservation.getDate_retrait();
            Date end = reservation.getDate_retour();

            if (start == null || end == null) {
                throw new IllegalArgumentException("Date de retrait ou de retour est nulle !");
            }

            LocalDate startDate = new java.sql.Date(start.getTime()).toLocalDate();
            LocalDate endDate = new java.sql.Date(end.getTime()).toLocalDate();

            long days = ChronoUnit.DAYS.between(startDate, endDate);
            if (days <= 0) days = 1;

            double total = tarif * days;
            montantTotal = total;

            tarifField.setText(String.valueOf(tarif));
            periodeField.setText(days + " jours");
            montantField.setText(String.valueOf(total));

            client = reservation.getClient();
            int points = client.getPts_fidelite();
            pointsLabel.setText("Vous avez " + points + " points de fidélité");

            reductionChoice.getItems().clear();
            reductionChoice.getItems().add("Aucune réduction");

            if (points >= 50) {
                reductionChoice.getItems().add("Utiliser 50 points (50% de réduction)");
            }
            if (points >= 100) {
                reductionChoice.getItems().add("Utiliser 100 points (Réservation gratuite)");
            }

            reductionChoice.setValue("Aucune réduction");
            reductionChoice.setOnAction(e -> {
                selectedReduction = reductionChoice.getValue();
                updateMontantField();
            });

            updateMontantField();
        } catch (Exception e) {
            showAlert("Erreur", "Impossible de calculer le montant : " + e.getMessage());
        }
    }

    @FXML
    private void onValiderClicked() {
        try {
            client = reservation.getClient();
            double soldeClient = client.getSolde();
            double montantFinal = montantTotal;

            int pointsToDeduct = 0;

            switch (selectedReduction) {
                case "Utiliser 50 points (50% de réduction)" -> {
                    montantFinal = montantTotal * 0.5;
                    pointsToDeduct = 50;
                }
                case "Utiliser 100 points (Réservation gratuite)" -> {
                    montantFinal = 0.0;
                    pointsToDeduct = 100;
                }
            }

            if (soldeClient < montantFinal) {
                showAlert("Solde insuffisant", "Votre solde est insuffisant pour cette réservation.");
                return;
            }
            client.setSolde(soldeClient - montantFinal);
            client.setPts_fidelite(client.getPts_fidelite() - pointsToDeduct + 10);

            testeur.updateObject(client, Client.class);

            reservation.setStatut("Enregistrée");
            testeur.ajouterObject(reservation, Reservation.class);

            Facture facture = new Facture("Facture de " + client.getNom(), reservation);
            testeur.ajouterObject(facture, Facture.class);

            showAlert("Succès", "Réservation enregistrée avec succès !");
            if (mainController != null) {
                mainController.loadReservationsPage();
            }

        } catch (DAOException e) {
            showAlert("Erreur", "Une erreur est survenue : " + e.getMessage());
        }
    }

    private void updateMontantField() {
        double displayMontant = montantTotal;

        switch (selectedReduction) {
            case "Utiliser 50 points (50% de réduction)" -> displayMontant = montantTotal * 0.5;
            case "Utiliser 100 points (Réservation gratuite)" -> displayMontant = 0.0;
        }
        montantField.setText(String.valueOf(displayMontant));
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Main.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Tableau de bord");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de retourner au formulaire.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    public void setMainController(Main mainController) {
        this.mainController = mainController;
    }
}
