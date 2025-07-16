package com.autorental.controllers;

import com.autorental.dao.impl.HibernateChauffeurDaoImpl;
import com.autorental.exceptions.DAOException;
import com.autorental.model.Chauffeur;
import com.autorental.runtime.Testeur;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GestionChauffeur {

    @FXML private TableView<Chauffeur> vehiculeTable;
    @FXML private TableColumn<Chauffeur, String> LastNameColumn;
    @FXML private TableColumn<Chauffeur, String> FirstNameColumn;
    @FXML private TableColumn<Chauffeur, String> availabilityColumn;

    private Chauffeur chauffeur;
    Testeur testeur = new Testeur();

    private ObservableList<Chauffeur> chauffeurList;

    @FXML
    public void initialize() {
        LastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        FirstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom()));
        availabilityColumn.setCellValueFactory(cellData -> {
            boolean dispo = cellData.getValue().getDispo();
            return new SimpleStringProperty(dispo ? "Disponible" : "Indisponible");
        });

        loadChauffeurs();
    }

    private void loadChauffeurs() {
        try {
            chauffeurList = FXCollections.observableArrayList(Testeur.listerObjects(Chauffeur.class));
            vehiculeTable.setItems(chauffeurList);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur lors du chargement des chauffeurs : " + e.getMessage());
        }
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

        try {
            Testeur.deleteObject(selected.getId(), Chauffeur.class);
            chauffeurList.remove(selected);
            showAlert("Chauffeur supprimé avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur lors de la suppression : " + e.getMessage());
        }
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