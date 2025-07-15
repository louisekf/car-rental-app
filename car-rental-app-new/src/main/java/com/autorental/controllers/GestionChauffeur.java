package com.autorental.controllers;

import com.autorental.dao.impl.HibernateChauffeurDaoImpl;
import com.autorental.model.Chauffeur;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GestionChauffeur {

    @FXML private TableView<Chauffeur> vehiculeTable;
    @FXML private TableColumn<Chauffeur, String> LastNameColumn;
    @FXML private TableColumn<Chauffeur, String> FirstNameColumn;
    @FXML private TableColumn<Chauffeur, String> availabilityColumn;

    private final HibernateChauffeurDaoImpl chauffeurDao = new HibernateChauffeurDaoImpl();

    private ObservableList<Chauffeur> chauffeurList;

    @FXML
    public void initialize() {
        LastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        FirstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom()));
        availabilityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDispo()));


        loadChauffeurs();
    }

    private void loadChauffeurs() {
        chauffeurList = FXCollections.observableArrayList(chauffeurDao.findAll());
        vehiculeTable.setItems(chauffeurList);
    }

    @FXML
    private void loadAjoutChauffeurPage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/AjoutChauffeur.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Ajout Chauffeur");
            stage.setScene(new Scene(root));
            stage.setOnHiding(e -> loadChauffeurs());
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadModifierChauffeurPage(ActionEvent event) {
        Chauffeur selected = vehiculeTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Veuillez sélectionner un chauffeur.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ModifierChauffeur.fxml"));
            Parent root = loader.load();
            ModifierChauffeur controller = loader.getController();
            controller.setChauffeur(selected);
            Stage stage = new Stage();
            stage.setTitle("Modifier Chauffeur");
            stage.setScene(new Scene(root));
            stage.setOnHiding(e -> loadChauffeurs());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteChauffeur(ActionEvent event) {
        Chauffeur selected = vehiculeTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Veuillez sélectionner un chauffeur à supprimer.");
            return;
        }
        chauffeurDao.delete(selected);
        chauffeurList.remove(selected);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleBackButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainAdmin.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Tableau de bord");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
