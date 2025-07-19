package com.autorental.controllers;

import com.autorental.dao.impl.HibernateReservationDaoImpl;
import com.autorental.exceptions.DAOException;
import com.autorental.model.Chauffeur;
import com.autorental.model.Client;
import com.autorental.model.Reservation;
import com.autorental.model.Vehicule;
import com.autorental.runtime.Testeur;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DashboardAdmin  {
    @FXML private Label lblClients;
    @FXML private Label lblReservations;
    @FXML private Label lblChauffeurs;
    @FXML private Label lblVehicules;

    @FXML private TableView<Reservation> tableReservations;
    @FXML private TableColumn<Reservation, String> clientColumn;
    @FXML private TableColumn<Reservation, String> vehiculeColumn;
    @FXML private TableColumn<Reservation, String> chauffeurColumn;
    @FXML private TableColumn<Reservation, Date> dateRetraitColumn;
    @FXML private TableColumn<Reservation, Date> dateRetourColumn;
    @FXML private TableColumn<Reservation, String> statusColumn;
    @FXML private ComboBox<String> comboBoxMois;

    private ObservableList<Reservation> reservationList;
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    HibernateReservationDaoImpl reservationDao = new HibernateReservationDaoImpl();

    @FXML
    public void initialize() throws DAOException {
        int nbClients = Testeur.countObjects(Client.class);
        lblClients.setText(String.valueOf(nbClients));
        int nbReservations = Testeur.countObjects(Reservation.class);
        lblReservations.setText(String.valueOf(nbReservations));
        int nbChauffeurs = Testeur.countObjects(Chauffeur.class);
        lblChauffeurs.setText(String.valueOf(nbChauffeurs));
        int nbVehicules = Testeur.countObjects(Vehicule.class);
        lblVehicules.setText(String.valueOf(nbVehicules));

        clientColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getClient().getNomComplet())
        );

        vehiculeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getVehicule().getNomVehicule())
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

        statusColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStatut())
        );

        loadReservations();

        comboBoxMois.getItems().addAll("Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre",
                "Octobre", "Novembre", "Decembre");
    }

    private void loadReservations() {
        try {
            List<Reservation> allReservations = reservationDao.listDecroissant();
            reservationList = FXCollections.observableArrayList(allReservations);
            tableReservations.setItems(reservationList);
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
