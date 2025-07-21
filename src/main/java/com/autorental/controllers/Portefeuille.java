package com.autorental.controllers;

import com.autorental.runtime.Testeur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.autorental.dao.impl.HibernateClientDaoImpl;
import com.autorental.model.Client;
import com.autorental.utils.Session;
import com.autorental.exceptions.DAOException;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Portefeuille {
    @FXML private TextField montantTextField;

    Testeur testeur = new Testeur();
    private Profile profileController;

    public void setProfileController(Profile controller) {
        this.profileController = controller;
    }

    public void onValiderClicked(ActionEvent actionEvent) {
        try {
            String montantStr = montantTextField.getText();

            if (montantStr == null || montantStr.trim().isEmpty()) {
                showAlert("Champ requis", "Veuillez entrer un montant.");
                return;
            }

            double montant = Double.parseDouble(montantStr);

            if (montant <= 0) {
                showAlert("Montant invalide", "Le montant doit être supérieur à zéro.");
                return;
            }

            String login = Session.getCurrentUser().getLogin();
            HibernateClientDaoImpl clientDao = new HibernateClientDaoImpl();
            Client client = clientDao.getClientByUserLogin(login);
            if (client == null) {
                showAlert("Erreur", "Client introuvable.");
                return;
            }

            double ancienSolde = client.getSolde();
            client.setSolde(ancienSolde + montant);
            testeur.updateObject(client, Client.class);

            showAlert("Succès", "Solde mis à jour avec succès !");
            montantTextField.clear();
            if (profileController != null) {
                profileController.updateWalletDisplay();
            }
        } catch (NumberFormatException e) {
            showAlert("Erreur de saisie", "Veuillez entrer un montant valide.");
        } catch (DAOException e) {
            showAlert("Erreur", "Erreur lors de la mise à jour : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void onAnnulerClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) montantTextField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
