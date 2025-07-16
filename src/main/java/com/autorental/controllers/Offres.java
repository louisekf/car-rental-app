package com.autorental.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Offres {
    @FXML private Button btnOne;

    private Profile profileController;

    public void setProfileController(Profile controller) {
        this.profileController = controller;
    }

    public void onBtnClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) btnOne.getScene().getWindow();
        stage.close();
    }
}
