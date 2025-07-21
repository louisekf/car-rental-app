package com.autorental.controllers;

import com.autorental.model.Client;
import com.autorental.runtime.Testeur;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class LesClients {

    @FXML private TableView<Client> reservations;

    @FXML private TableColumn<Client, Integer> colId;
    @FXML private TableColumn<Client, String> colNom;
    @FXML private TableColumn<Client, String> colPrenom;
    @FXML private TableColumn<Client, String> colEmail;

    @FXML private TableColumn<Client, String> colAdresse;
    @FXML private TableColumn<Client, Integer> colFidelite;

    @FXML private TextField idField;
    @FXML private Button rechercherBtn;

    private ObservableList<Client> clientsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        colId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        colNom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        colPrenom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom()));
        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        colAdresse.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresse()));
        colFidelite.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPts_fidelite()).asObject());

        loadClients();

        rechercherBtn.setOnAction(event -> rechercherClient());
    }

    private void loadClients() {
        try {
            clientsList = FXCollections.observableArrayList(Testeur.listerObjects(Client.class));
            reservations.setItems(clientsList);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur lors du chargement des clients : " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void rechercherClient() {
        String keyword = idField.getText().toLowerCase().trim();
        if (keyword.isEmpty()) {
            reservations.setItems(clientsList);
            return;
        }

        ObservableList<Client> filtered = FXCollections.observableArrayList();
        for (Client c : clientsList) {
            if (String.valueOf(c.getId()).contains(keyword) ||
                    c.getNom().toLowerCase().contains(keyword) ||
                    c.getPrenom().toLowerCase().contains(keyword) ||
                    c.getEmail().toLowerCase().contains(keyword) ||
                    (c.getTel() != null && c.getTel().toLowerCase().contains(keyword))) {
                filtered.add(c);
            }
        }
        reservations.setItems(filtered);
    }
}
