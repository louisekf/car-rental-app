package com.autorental.controllers;

import com.autorental.dao.impl.HibernateObjectDaoImpl;
import com.autorental.dao.impl.HibernateVehiculeDaoImpl;
import com.autorental.exceptions.DAOException;
import com.autorental.model.Vehicule;
import com.autorental.runtime.Testeur;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GestionVehicules {
    @FXML
    private AnchorPane mainContentPane; // Injection automatique par JavaFX

    @FXML private TableView<Vehicule> vehiculeTable;
    @FXML private TableColumn<Vehicule, String> brandColumn;
    @FXML private TableColumn<Vehicule, String> modelColumn;
    @FXML private TableColumn<Vehicule, String> licensePlateColumn;
    @FXML private TableColumn<Vehicule, String> tarifColumn;
    @FXML private TableColumn<Vehicule, String> availabilityColumn;

    private Testeur testeur = new Testeur();
    private ObservableList<Vehicule> vehiculeList;

    @FXML
    public void initialize() throws DAOException {
        brandColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getMarque()));

        modelColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getModele()));

        licensePlateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getImmatriculation()));

       tarifColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(
                cellData.getValue().getTarif()).asString());

        availabilityColumn.setCellValueFactory(cellData -> {
            boolean dispo = cellData.getValue().isDispo(); // ou getDisponible()
            String valeurTexte = dispo ? "Disponible" : "Indisponible";
            return new SimpleStringProperty(valeurTexte);
        });

        loadVehicules();
    }

    private void loadVehicules() throws DAOException {
        vehiculeList = FXCollections.observableArrayList(testeur.listerObjects(Vehicule.class));
        vehiculeTable.setItems(vehiculeList);
    }

    @FXML
    private void loadAjoutVehiculePage() {
        if (mainContentPane == null) {
            System.err.println("Erreur : mainContentPane est null !");
            return;
        }
        try {
            AnchorPane ajoutPane = FXMLLoader.load(getClass().getResource("/views/AjoutVehicule.fxml"));
            mainContentPane.getChildren().setAll(ajoutPane);
            AnchorPane.setTopAnchor(ajoutPane, 0.0);
            AnchorPane.setBottomAnchor(ajoutPane, 0.0);
            AnchorPane.setLeftAnchor(ajoutPane, 0.0);
            AnchorPane.setRightAnchor(ajoutPane, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/*
    @FXML
    private void loadModifierVehiculePage() {
        if (mainContentPane == null) return;
        try {
            AnchorPane modifPane = FXMLLoader.load(getClass().getResource("/views/ModifierVehicule.fxml"));
            mainContentPane.getChildren().setAll(modifPane);
            AnchorPane.setTopAnchor(modifPane, 0.0);
            AnchorPane.setBottomAnchor(modifPane, 0.0);
            AnchorPane.setLeftAnchor(modifPane, 0.0);
            AnchorPane.setRightAnchor(modifPane, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
    @FXML
    private void loadModifierVehiculePage(ActionEvent event) throws IOException {
        Vehicule selected = vehiculeTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Veuillez sélectionner un vehicule.");
            return;
        }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ModifierVehicule.fxml"));
                AnchorPane modifyPane = loader.load();

                ModifierVehicule controller = loader.getController();
                controller.setVehicule(selected);

                mainContentPane.getChildren().setAll(modifyPane);
                AnchorPane.setTopAnchor(modifyPane, 0.0);
                AnchorPane.setBottomAnchor(modifyPane, 0.0);
                AnchorPane.setLeftAnchor(modifyPane, 0.0);
                AnchorPane.setRightAnchor(modifyPane, 0.0);
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public void onDeleteVehicule(ActionEvent actionEvent) throws DAOException {
        Vehicule selected = vehiculeTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Veuillez sélectionner un vehicule à supprimer.");
            return;
        }
        Testeur.deleteObject(selected.getId(), Vehicule.class);
        //vehiculeTable.getItems().remove(selected);
        vehiculeList.remove(selected);
        showAlert("Véhicule supprimé avec succès.");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
