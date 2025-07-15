package com.autorental.controllers;

import com.autorental.dao.impl.HibernateReservationDaoImpl;
import com.autorental.model.Reservation;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GestionReservations {
    @FXML private Button btnChangerStatut;

    @FXML private TableView<Reservation> RentalTable;
    @FXML private TableColumn<Reservation, String> clientColumn;
    @FXML private TableColumn<Reservation, String> vehiculeColumn;
    @FXML private TableColumn<Reservation, String> chauffeurColumn;
    @FXML private TableColumn<Reservation, LocalDateTime> dateRetraitColumn;
    @FXML private TableColumn<Reservation, LocalDateTime> dateRetourColumn;
    @FXML private TableColumn<Reservation, String> statutColumn;

    private final HibernateReservationDaoImpl reservationDao = new HibernateReservationDaoImpl();
    private ObservableList<Reservation> reservationList;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @FXML
    public void initialize() {
        clientColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getClient().getNomComplet()));

        vehiculeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getVehicule().getMarqueModele()));

        chauffeurColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getChauffeur() != null ? cellData.getValue().getChauffeur().getNomComplet() : "Aucun"));

        dateRetraitColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDate_retrait()));
        dateRetraitColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : formatter.format(item));
            }
        });

        dateRetourColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDate_retour()));
        dateRetourColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : formatter.format(item));
            }
        });

        statutColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatut()));

        loadReservations();
    }

    @FXML
    private void handleChangerStatut() {
        Reservation selected = RentalTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner une réservation.");
            alert.showAndWait();
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

            // Mise à jour locale dans la table après modification
            int selectedIndex = reservationList.indexOf(selected);
            if (selectedIndex >= 0) {
                reservationList.set(selectedIndex, selected);
            }

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors du chargement du formulaire.");
            alert.showAndWait();
        }
    }

    private void loadReservations() {
        reservationList = FXCollections.observableArrayList(reservationDao.findAll());
        RentalTable.setItems(reservationList);
    }
}
