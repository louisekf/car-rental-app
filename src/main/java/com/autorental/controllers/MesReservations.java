package com.autorental.controllers;

import com.autorental.dao.impl.HibernateClientDaoImpl;
import com.autorental.exceptions.DAOException;
import com.autorental.model.Chauffeur;
import com.autorental.model.Client;
import com.autorental.model.Reservation;
import com.autorental.model.Vehicule;
import com.autorental.runtime.Testeur;
import com.autorental.utils.Session;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class MesReservations implements Initializable {

    @FXML private TextField idField;
    @FXML private TableView<Reservation> reservations;
    @FXML private TableColumn<Reservation, Integer> colId;
    @FXML private TableColumn<Reservation, Vehicule> colVehicule;
    @FXML private TableColumn<Reservation, Date> colDate_retrait;
    @FXML private TableColumn<Reservation, Date> colDate_rtr;
    @FXML private TableColumn<Reservation, String> colChauffeur;
    @FXML private TableColumn<Reservation, String> colStatut;

    private final ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    private Reservation reservation;
    Testeur testeur = new Testeur();
    private Notifications notificationsController;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupColumns();
        loadReservationsOfConnectedUser();
    }

    private void loadReservationsOfConnectedUser() {
        try {
            String login = Session.getCurrentUser().getLogin();

            HibernateClientDaoImpl clientDao = new HibernateClientDaoImpl();
            Client client = clientDao.getClientByUserLogin(login);
            if (client == null) {
                System.out.println("Client non trouvé.");
                return;
            }
            List<Reservation> results = testeur.listerObjects(Reservation.class);
            List<Reservation> filtered = results.stream()
                    .filter(res -> res.getClient() != null && res.getClient().getId() == client.getId())
                    .toList();
            reservationList.setAll(filtered);
            reservations.setItems(reservationList);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    private void setupColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colVehicule.setCellValueFactory(new PropertyValueFactory<>("vehicule"));
        colDate_retrait.setCellValueFactory(new PropertyValueFactory<>("date_retrait"));
        colDate_retrait.setCellFactory(column -> new TableCell<Reservation, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : dateFormatter.format(item));
            }
        });
        colDate_rtr.setCellValueFactory(new PropertyValueFactory<>("date_retour"));
        colDate_rtr.setCellFactory(column -> new TableCell<Reservation, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : dateFormatter.format(item));
            }
        });
        colChauffeur.setCellValueFactory(cellData -> {
            Chauffeur chauffeur = cellData.getValue().getChauffeur();
            String display = (chauffeur != null) ? chauffeur.toString() : "Aucun";
            return new SimpleStringProperty(display);
        });
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));
    }

    public void onRechercherClicked(ActionEvent actionEvent) {
        try {
            String idArechercher = idField.getText();
            if (idArechercher == null || idArechercher.trim().isEmpty()) {
                showAlert("Champ requis", "Veuillez entrer un ID de réservation.");
                return;
            }

            int id = Integer.parseInt(idArechercher);
            reservation = testeur.rechercherObject(id, Reservation.class);

            if (reservation == null) {
                showAlert("Introuvable", "Aucune réservation trouvée avec cet ID.");
                return;
            }

            String login = Session.getCurrentUser().getLogin();
            HibernateClientDaoImpl clientDao = new HibernateClientDaoImpl();
            Client currentClient = clientDao.getClientByUserLogin(login);

            if (reservation.getClient().getId() != currentClient.getId()) {
                showAlert("Accès refusé", "Cette réservation n'appartient pas à votre compte.");
                return;
            }
            String info = "ID: " + reservation.getId() +
                    "\nVéhicule: " + reservation.getVehicule().getMarque() +
                    "\nStatut: " + reservation.getStatut();
            showAlert("Réservation trouvée", info);

        } catch (NumberFormatException e) {
            showAlert("Erreur", "L'ID doit être un nombre entier.");
        } catch (DAOException e) {
            showAlert("Erreur", "Erreur lors de la recherche : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void onPayerClicked(ActionEvent actionEvent) {
        Reservation selectedReservation = reservations.getSelectionModel().getSelectedItem();

        if (selectedReservation == null) {
            showAlert("Sélection requise", "Veuillez sélectionner une réservation à payer.");
            return;
        }
        if (!"Validée".equalsIgnoreCase(selectedReservation.getStatut())) {
            showAlert("Paiement impossible", "Seules les réservations validées peuvent être payées.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/FactureClient.fxml"));
            Parent factureRoot = loader.load();

            FactureClient controller = loader.getController();
            controller.setReservation(selectedReservation);
            controller.setMesReservationsController(this);
            controller.setNotificationsController(notificationsController);

            Stage stage = new Stage();
            stage.setTitle("Facturation");
            stage.setScene(new Scene(factureRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ouvrir la page de facture : " + e.getMessage());
        }
    }

    public void refreshReservationsTable() {
        loadReservationsOfConnectedUser();
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
