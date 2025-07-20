package com.autorental.controllers;

import com.autorental.model.Notification;
import com.autorental.runtime.Testeur;
import com.autorental.utils.Session;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;
import java.util.stream.Collectors;

public class Notifications {

    @FXML
    private ListView<String> notificationListView;

    private ObservableList<String> userMessages = FXCollections.observableArrayList();
    private Testeur testeur = new Testeur();

    @FXML
    public void initialize() {
        notificationListView.setItems(userMessages);
        refreshNotifications();
    }

    public void refreshNotifications() {
        int userId = Session.getCurrentUser().getId();
        Platform.runLater(() -> {
            try {
                userMessages.clear();
                List<Notification> allNotifications = testeur.listerObjects(Notification.class);
                List<String> filtered = allNotifications.stream()
                        .filter(n -> n.getUserId() == userId)
                        .map(n -> "🔔 " + n.getMessage())
                        .collect(Collectors.toList());

                if (filtered.isEmpty()) {
                    userMessages.add("Aucune notification pour le moment.");
                } else {
                    userMessages.addAll(filtered);
                }
            } catch (Exception e) {
                e.printStackTrace();
                userMessages.clear();
                userMessages.add("Erreur lors du chargement des notifications.");
            }
        });
    }
}
