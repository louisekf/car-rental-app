package com.autorental.controllers;

import com.autorental.db.HibernateConnection;
import com.autorental.exceptions.DAOException;
import com.autorental.model.Vehicule;
import com.autorental.runtime.Testeur;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Accueil implements Initializable {

    @FXML private TableView<Vehicule> vehicules;
    @FXML private TableColumn<Vehicule, String> colMarque;
    @FXML private TableColumn<Vehicule, String> colModele;
    @FXML private TableColumn<Vehicule, Double> colTarif;
    @FXML private TableColumn<Vehicule, String> colDispo;
    @FXML private TableColumn<Vehicule, String> colImmat;

    private final ObservableList<Vehicule> vehiculeList = FXCollections.observableArrayList();

    Testeur testeur = new Testeur();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupColumns();
        loadVehicules();
    }

    private void setupColumns() {
        colMarque.setCellValueFactory(new PropertyValueFactory<>("marque"));
        colModele.setCellValueFactory(new PropertyValueFactory<>("modele"));
        colTarif.setCellValueFactory(new PropertyValueFactory<>("tarif"));
        colDispo.setCellValueFactory(cellData -> {
            boolean dispo = cellData.getValue().isDispo();
            String status = dispo ? "Disponible" : "Indisponible";
            return new ReadOnlyStringWrapper(status);
        });
        colImmat.setCellValueFactory(new PropertyValueFactory<>("immatriculation"));
    }

    private void loadVehicules() {
        try {
            List<Vehicule> results = testeur.listerObjects(Vehicule.class);
            vehiculeList.setAll(results);
            vehicules.setItems(vehiculeList);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
