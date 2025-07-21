package com.autorental.controllers;

import com.autorental.dao.impl.HibernateClientDaoImpl;
import com.autorental.exceptions.DAOException;
import com.autorental.model.Client;
import com.autorental.model.Reservation;
import com.autorental.model.Vehicule;
import com.autorental.runtime.Testeur;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Date;

public class LesClients {
    @FXML
    private TableView<Client> clientsTable;
    @FXML private TableColumn<Client, String> colId;
    @FXML private TableColumn<Client, String> colNom;
    @FXML private TableColumn<Client, String> colPrenom;
    @FXML private TableColumn<Client, String> colEmail;
    @FXML private TableColumn<Client, String> colAdresse;
    @FXML private TableColumn<Client, String> colPoints;

    @FXML private TextField idField;

    private ObservableList<Client> clientList;
    private HibernateClientDaoImpl clientDao = new HibernateClientDaoImpl();
    private Client client;

    @FXML
    private void initialize() throws DAOException {
        colId.setCellValueFactory(cellData -> new SimpleObjectProperty(
                cellData.getValue().getId()).asString());

        colNom.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getNom()));

        colPrenom.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getPrenom()));

        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getEmail()));

        colAdresse.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getAdresse()));

        colPoints.setCellValueFactory(cellData -> new SimpleObjectProperty(
                cellData.getValue().getPts_fidelite()).asString());
        loadClients();
    }

    private void loadClients() throws DAOException {
        clientList = FXCollections.observableArrayList(Testeur.listerObjects(Client.class));
        clientsTable.setItems(clientList);
    }

    @FXML
    private void onRechercher(ActionEvent actionEvent) throws DAOException {
        ObservableList<Client> clients = FXCollections.observableArrayList();

        String input = idField.getText().trim();

        if (!input.isEmpty()) {
            if (input.matches("\\d+")) { // uniquement des chiffres → ID
                try {
                    int id = Integer.parseInt(input);
                    Client client = Testeur.rechercherObject(id, Client.class);
                    if (client != null) {
                        clients.add(client);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("ID invalide !");
                }
            } else {
                clients.addAll(clientDao.findByNomOuPrenom(input));
            }
        } else {
            loadClients();
        }

        clientsTable.setItems(clients);
    }
}
