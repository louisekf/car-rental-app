package com.autorental.controllers;

import com.autorental.model.Reservation;
import com.autorental.runtime.Testeur;
import com.autorental.exceptions.DAOException;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GestionReservations {

    @FXML private TableView<Reservation> RentalTable;
    @FXML private TableColumn<Reservation, String> clientColumn;
    @FXML private TableColumn<Reservation, String> vehiculeColumn;
    @FXML private TableColumn<Reservation, String> chauffeurColumn;
    @FXML private TableColumn<Reservation, Date> dateRetraitColumn;
    @FXML private TableColumn<Reservation, Date> dateRetourColumn;
    @FXML private TableColumn<Reservation, String> statutColumn;

    private ObservableList<Reservation> reservationList;
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    Testeur testeur = new Testeur();

    @FXML
    public void initialize() {
        clientColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getClient().toString())
        );

        vehiculeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getVehicule().toString())
        );

        chauffeurColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getChauffeur() != null ?
                                cellData.getValue().getChauffeur().toString() : "Aucun"
                )
        );

        dateRetraitColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getDate_retrait())
        );
        dateRetraitColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : dateFormatter.format(item));
            }
        });

        dateRetourColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getDate_retour())
        );
        dateRetourColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : dateFormatter.format(item));
            }
        });

        statutColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStatut())
        );

        loadReservations();
    }

    @FXML
    private void handleChangerStatut() {
        Reservation selected = RentalTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Veuillez sélectionner une réservation.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ModifierStatutReservation.fxml"));
            Parent root = loader.load();

            ModifierStatutReservation controller = loader.getController();
            controller.setReservation(selected);

            Stage stage = new Stage();
            stage.setTitle("Modifier le statut");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            RentalTable.refresh();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur lors du chargement du formulaire.");
        }
    }

    private void loadReservations() {
        try {
            List<Reservation> allReservations = testeur.listerObjects(Reservation.class);
            reservationList = FXCollections.observableArrayList(allReservations);
            RentalTable.setItems(reservationList);
        } catch (DAOException e) {
            e.printStackTrace();
            showAlert("Erreur lors du chargement des réservations : " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
