package com.autorental.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Authentification {
    @FXML
    private void goToInscription(ActionEvent event) throws IOException {
        Parent inscriptionPage = FXMLLoader.load(getClass().getResource("/views/Inscription.fxml"));
        Scene scene = new Scene(inscriptionPage);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
