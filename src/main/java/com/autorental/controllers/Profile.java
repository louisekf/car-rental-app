package com.autorental.controllers;

import com.autorental.model.Client;
import com.autorental.model.Reservation;
import com.autorental.model.User;
import com.autorental.utils.Session;
import com.autorental.dao.impl.HibernateClientDaoImpl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Profile implements Initializable {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;
    @FXML private TextField telField;
    @FXML private TextField adresseField;
    @FXML private PasswordField passwordField;
    @FXML private Label walletLabel;
    @FXML private Label pointsLabel;

    private final HibernateClientDaoImpl clientDao = new HibernateClientDaoImpl();
    private Reservation reservation;
    private Main mainController;

    public void setMainController(Main mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = Session.getCurrentUser();

        if (user != null && user.getRole().equalsIgnoreCase("CLIENT")) {
            Client client = clientDao.getClientByUserLogin(user.getLogin());
            if (client != null) {
                nomField.setText(client.getNom());
                prenomField.setText(client.getPrenom());
                emailField.setText(client.getEmail());
                telField.setText(client.getTel());
                adresseField.setText(client.getAdresse());
                pointsLabel.setText(String.valueOf(client.getPts_fidelite()));
                walletLabel.setText(String.valueOf(client.getSolde()));
                passwordField.setText(user.getPassword());
            }
        }
    }

    public void onDeposerClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Portefeuille.fxml"));
            Scene scene = new Scene(loader.load());

            Portefeuille controller = loader.getController();
            controller.setProfileController(this);

            Stage popupStage = new Stage();
            popupStage.setTitle("Recharger le portefeuille");
            popupStage.setScene(scene);
            popupStage.setResizable(false);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateWalletDisplay() {
        String login = Session.getCurrentUser().getLogin();
        Client client = clientDao.getClientByUserLogin(login);
        if (client != null) {
            walletLabel.setText(String.valueOf(client.getSolde()));
            pointsLabel.setText(String.valueOf(client.getPts_fidelite()));
        }
    }

    public void onCadeauClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Offres.fxml"));
            Scene scene = new Scene(loader.load());

            Offres controller = loader.getController();
            controller.setProfileController(this);

            Stage popupStage = new Stage();
            popupStage.setTitle("Vos offres");
            popupStage.setScene(scene);
            popupStage.setResizable(false);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onUtiliserClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Convertir.fxml"));
            Scene scene = new Scene(loader.load());

            Convertir controller = loader.getController();
            controller.setProfileController(this);

            Stage popupStage = new Stage();
            popupStage.setTitle("Convertir vos points");
            popupStage.setScene(scene);
            popupStage.setResizable(false);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
