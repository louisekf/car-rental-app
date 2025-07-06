package com.autorental.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class GestionChauffeur {

    private AnchorPane mainContentPane;

    public void setMainContentPane(AnchorPane contentPane) {
        this.mainContentPane = contentPane;
    }

    @FXML
    private void loadAjoutChauffeurPage() {
        try {
            AnchorPane ajoutPane = FXMLLoader.load(getClass().getResource("/views/AjoutChauffeur.fxml"));
            mainContentPane.getChildren().setAll(ajoutPane);
            AnchorPane.setTopAnchor(ajoutPane, 0.0);
            AnchorPane.setBottomAnchor(ajoutPane, 0.0);
            AnchorPane.setLeftAnchor(ajoutPane, 0.0);
            AnchorPane.setRightAnchor(ajoutPane, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadModifierChauffeurPage() {
        try {
            AnchorPane modifPane = FXMLLoader.load(getClass().getResource("/views/ModifierChauffeur.fxml"));
            mainContentPane.getChildren().setAll(modifPane);
            AnchorPane.setTopAnchor(modifPane, 0.0);
            AnchorPane.setBottomAnchor(modifPane, 0.0);
            AnchorPane.setLeftAnchor(modifPane, 0.0);
            AnchorPane.setRightAnchor(modifPane, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
