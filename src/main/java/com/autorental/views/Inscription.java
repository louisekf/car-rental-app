package com.autorental.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Inscription {
    @FXML
    private void backToLogin(ActionEvent event) throws IOException {
        Parent loginPage = FXMLLoader.load(getClass().getResource("/views/Authentification.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loginPage));
        stage.show();
    }

    @FXML
    private void goToMain(ActionEvent event) throws IOException {
        Parent mainPage = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(mainPage));
        stage.show();
    }
}
