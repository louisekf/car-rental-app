package com.autorental.controllers;

import com.autorental.dao.impl.HibernateClientDaoImpl;
import com.autorental.dao.impl.HibernateUserDaoImpl;
import com.autorental.model.*;
import com.autorental.runtime.Testeur;
import com.autorental.exceptions.DAOException;
import com.autorental.utils.Session;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.List;

public class Formulaire {

    @FXML private ComboBox<Vehicule> vehiculeComboBox;
    @FXML private ComboBox<String> typeReservationComboBox;
    @FXML private ComboBox<Chauffeur> chauffeurComboBox;
    @FXML private DatePicker dateRetraitPicker;
    @FXML private DatePicker dateRetourPicker;

    private Reservation reservationTemp;
    private Reservation reservation;
    private User user;

    Testeur testeur = new Testeur();
    HibernateUserDaoImpl userDao = new HibernateUserDaoImpl();

    private Main mainController;

    public void setMainController(Main mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        selectTypeReservation();
        loadVehicules();
        loadChauffeurs();
        setupTypeReservationLogic();
        dateCheck();
    }

    private Callback<DatePicker, DateCell> getDayCellFactory(LocalDate minDate) {
        return datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty || item.isBefore(minDate));
            }
        };
    }

    private void dateCheck(){
        LocalDate today = LocalDate.now();
        dateRetraitPicker.setDayCellFactory(getDayCellFactory(today));
        dateRetourPicker.setDayCellFactory(getDayCellFactory(today));

        dateRetraitPicker.valueProperty().addListener((obs, oldDate, newDate) -> {
            if (newDate != null) {
                dateRetourPicker.setDayCellFactory(getDayCellFactory(newDate));
            }
        });

    }

    private void selectTypeReservation() {
        typeReservationComboBox.setItems(FXCollections.observableArrayList("Avec chauffeur", "Sans chauffeur"));
    }

    private void loadVehicules() {
        try {
            List<Vehicule> vehicules = testeur.listerObjects(Vehicule.class)
                    .stream()
                    .filter(Vehicule::isDispo)
                    .toList();

            vehiculeComboBox.setItems(FXCollections.observableArrayList(vehicules));
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    private void loadChauffeurs() {
        try {
            List<Chauffeur> chauffeurs = testeur.listerObjects(Chauffeur.class)
                    .stream().filter(Chauffeur::getDispo).toList();
            chauffeurComboBox.setItems(FXCollections.observableArrayList(chauffeurs));
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    private void setupTypeReservationLogic() {
        typeReservationComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            boolean withDriver = "Avec chauffeur".equals(newVal);
            chauffeurComboBox.setDisable(!withDriver);
        });
    }

    @FXML
    private void onSuivantClicked() {
        try {
            Vehicule vehicule = vehiculeComboBox.getValue();
            String type = typeReservationComboBox.getValue();
            Chauffeur chauffeur = chauffeurComboBox.getValue();
            LocalDate dateRetrait = dateRetraitPicker.getValue();
            LocalDate dateRetour = dateRetourPicker.getValue();

            if (vehicule == null || dateRetrait == null || dateRetour == null || type == null) {
                showAlert("Veuillez remplir tous les champs requis.");
                return;
            }

            String login = Session.getCurrentUser().getLogin();
            HibernateClientDaoImpl clientDao = new HibernateClientDaoImpl();
            Client client = clientDao.getClientByUserLogin(login);
            if (client == null) {
                showAlert("Impossible de retrouver le client connecté.");
                return;
            }

            reservationTemp = new Reservation();
            reservationTemp.setClient(client);
            reservationTemp.setVehicule(vehicule);
            reservationTemp.setDate_retrait(java.sql.Date.valueOf(dateRetrait));
            reservationTemp.setDate_retour(java.sql.Date.valueOf(dateRetour));
            reservationTemp.setStatut("En attente");

            if ("Avec chauffeur".equals(type)) {
                if (chauffeur == null) {
                    showAlert("Veuillez choisir un chauffeur.");
                    return;
                }
                reservationTemp.setChauffeur(chauffeur);
            }
            Testeur.ajouterObject(reservationTemp, Reservation.class);

            //Notifier les admins
            List<User> allUsers = Testeur.listerObjects(User.class);
            List<User> admins = allUsers.stream()
                    .filter(user -> "admin".equalsIgnoreCase(user.getRole()))
                    .toList();
            String clientName = client.getPrenom() + " " + client.getNom();
            String notifMessage = "Nouvelle réservation en attente par le client " + clientName + ".";
            for (User admin : admins) {
                Notification notification = new Notification(notifMessage, admin.getId());
                Testeur.ajouterObject(notification, Notification.class);
            }
            //Notifier le client
            String emailClient = reservationTemp.getClient().getEmail();
            user = userDao.getUserByClientEmail(emailClient);
            if (user!= null) {
                int userId = user.getId();
                String notifMessageClient = "Réservation enregistrée avec succès! En attente de validation.";
                Notification notif = new Notification(notifMessageClient, userId);
                testeur.ajouterObject(notif, Notification.class);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Réservation envoyée");
            alert.setHeaderText(null);
            alert.setContentText("Votre réservation a été envoyée.");
            alert.showAndWait();

            mainController.loadAccueilPage();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur lors de la création de la réservation.");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Champ manquant");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
