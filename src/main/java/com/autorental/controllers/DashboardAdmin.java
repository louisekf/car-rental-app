package com.autorental.controllers;

import com.autorental.dao.impl.HibernateFactureDaoImpl;
import com.autorental.dao.impl.HibernateReservationDaoImpl;
import com.autorental.exceptions.DAOException;
import com.autorental.model.Chauffeur;
import com.autorental.model.Client;
import com.autorental.model.Reservation;
import com.autorental.model.Vehicule;
import com.autorental.runtime.Testeur;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class DashboardAdmin  {
    @FXML private Label lblClients;
    @FXML private Label lblReservations;
    @FXML private Label lblChauffeurs;
    @FXML private Label lblVehicules;

    @FXML private TableView<Reservation> tableReservations;
    @FXML private TableColumn<Reservation, String> clientColumn;
    @FXML private TableColumn<Reservation, String> vehiculeColumn;
    @FXML private TableColumn<Reservation, String> chauffeurColumn;
    @FXML private TableColumn<Reservation, Date> dateRetraitColumn;
    @FXML private TableColumn<Reservation, Date> dateRetourColumn;
    @FXML private TableColumn<Reservation, String> statusColumn;
    @FXML private ComboBox<String> comboBoxMois;

    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;

    @FXML
    LineChart<String, Number> chartRevenus; // = new LineChart<String, Number>(xAxis, yAxis);


    private ObservableList<Reservation> reservationList;
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    HibernateReservationDaoImpl reservationDao = new HibernateReservationDaoImpl();

    @FXML
    public void initialize() throws DAOException {
        int nbClients = Testeur.countObjects(Client.class);
        lblClients.setText(String.valueOf(nbClients));
        int nbReservations = Testeur.countObjects(Reservation.class);
        lblReservations.setText(String.valueOf(nbReservations));
        int nbChauffeurs = Testeur.countObjects(Chauffeur.class);
        lblChauffeurs.setText(String.valueOf(nbChauffeurs));
        int nbVehicules = Testeur.countObjects(Vehicule.class);
        lblVehicules.setText(String.valueOf(nbVehicules));

        clientColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getClient().getNomComplet())
        );

        vehiculeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getVehicule().getNomVehicule())
        );

        chauffeurColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getChauffeur() != null ?
                                cellData.getValue().getChauffeur().toString() : "Aucun"
                )
        );

        dateRetraitColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getDate_retrait())
        );
        dateRetraitColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : dateFormatter.format(item));
            }
        });

        dateRetourColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getDate_retour())
        );
        dateRetourColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : dateFormatter.format(item));
            }
        });

        statusColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStatut())
        );

        loadReservations();

        comboBoxMois.getItems().addAll("Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre",
                "Octobre", "Novembre", "Decembre");

        comboBoxMois.setValue("Juillet");
        comboBoxMois.setOnAction(event -> {
            try {
                afficherCourbeRevenus();
            } catch (DAOException e) {
                e.printStackTrace();
            }
        });

        afficherCourbeRevenus();
    }

    private void loadReservations() {
        try {
            List<Reservation> allReservations = reservationDao.listDecroissant();
            reservationList = FXCollections.observableArrayList(allReservations);
            tableReservations.setItems(reservationList);
        } catch (DAOException e) {
            e.printStackTrace();
            showAlert("Erreur lors du chargement des réservations : " + e.getMessage());
        }
    }

    private Map<String, Double> calculerRevenusParPeriode(List<Object[]> data) {
        Map<String, Double> revenusParPeriode = new LinkedHashMap<>();

        // Initialiser les périodes
        String[] periodes = { "1", "5", "10", "15", "20", "25", "30" };
        for (String periode : periodes) {
            revenusParPeriode.put(periode, 0.0);
        }

        for (Object[] row : data) {
            Date dateRetrait = (Date) row[0];
            double montant = (double) row[1];

            Calendar cal = Calendar.getInstance();
            cal.setTime(dateRetrait);
            int jour = cal.get(Calendar.DAY_OF_MONTH);

            String key = switch (jour) {
                case 1, 2, 3, 4 -> "1";
                case 5, 6, 7, 8, 9 -> "5";
                case 10, 11, 12, 13, 14 -> "10";
                case 15, 16, 17, 18, 19 -> "15";
                case 20, 21, 22, 23, 24 -> "20";
                case 25, 26, 27, 28, 29 -> "25";
                default -> "30";
            };

            revenusParPeriode.put(key, revenusParPeriode.get(key) + montant);
        }

        return revenusParPeriode;
    }

    private void afficherCourbeRevenus() throws DAOException {
        String moisSelectionne = comboBoxMois.getValue();
        if (moisSelectionne == null) return;

        int moisIndex = getMoisIndex(moisSelectionne);
        if (moisIndex == -1) return;

        HibernateFactureDaoImpl factureDao = new HibernateFactureDaoImpl();
        List<Object[]> revenusData = factureDao.getRevenusParDateRetrait();

        // Filtrer par mois seulement (toutes années confondues)
        List<Object[]> dataFiltree = revenusData.stream()
                .filter(row -> {
                    Date date = (Date) row[0];
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    return cal.get(Calendar.MONTH) + 1 == moisIndex;
                })
                .toList();

        Map<String, Double> revenusParPeriode = calculerRevenusParPeriode(dataFiltree);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Revenus - " + moisSelectionne);

        for (Map.Entry<String, Double> entry : revenusParPeriode.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        chartRevenus.getData().clear();
        chartRevenus.getData().add(series);
    }

    private int getMoisIndex(String nomMois) {
        return switch (nomMois.toLowerCase()) {
            case "janvier" -> 1;
            case "fevrier" -> 2;
            case "mars" -> 3;
            case "avril" -> 4;
            case "mai" -> 5;
            case "juin" -> 6;
            case "juillet" -> 7;
            case "aout" -> 8;
            case "septembre" -> 9;
            case "octobre" -> 10;
            case "novembre" -> 11;
            case "decembre" -> 12;
            default -> -1;
        };
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
