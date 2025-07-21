package com.autorental.controllers;

import com.autorental.dao.impl.HibernateClientDaoImpl;
import com.autorental.model.Client;
import com.autorental.runtime.Testeur;
import com.autorental.utils.Session;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class Convertir {

    @FXML private Label lblAvailablePoints;
    @FXML private TextField txtPointsToConvert;
    @FXML private Label lblResult;

    private Client client;
    private final Testeur testeur = new Testeur();
    HibernateClientDaoImpl clientDao = new HibernateClientDaoImpl();

    private final int conversionRate = 100; // 1 point = 100 FCFA

    private Profile profileController;

    public void setProfileController(Profile controller) {
        this.profileController = controller;
    }

    @FXML
    public void initialize() {
        String login = Session.getCurrentUser().getLogin();

        client = clientDao.getClientByUserLogin(login);
        if (client == null) {
            System.out.println("Client non trouvé.");
            return;
        }

        lblAvailablePoints.setText("Points disponibles : " + client.getPts_fidelite());
        lblResult.setText("");
    }

    @FXML
    private void handleConvertir() {
        try {
            int pointsToConvert = Integer.parseInt(txtPointsToConvert.getText());

            if (pointsToConvert <= 0) {
                lblResult.setText("Veuillez entrer un nombre de points valide.");
                return;
            }

            if (pointsToConvert > client.getPts_fidelite()) {
                lblResult.setText("Points insuffisants.");
                return;
            }

            double montantAjoute = pointsToConvert * conversionRate;
            client.setPts_fidelite(client.getPts_fidelite() - pointsToConvert);
            client.setSolde(client.getSolde() + montantAjoute);

            testeur.updateObject(client, Client.class);

            lblResult.setText("Conversion réussie : " + montantAjoute + " FCFA ajoutés !");
            lblAvailablePoints.setText("Points disponibles : " + client.getPts_fidelite());
            txtPointsToConvert.clear();
            if (profileController != null) {
                profileController.updateWalletDisplay();
            }
        } catch (NumberFormatException e) {
            lblResult.setText("Veuillez entrer un nombre valide.");
        } catch (Exception e) {
            lblResult.setText("Erreur : " + e.getMessage());
        }
    }

}
