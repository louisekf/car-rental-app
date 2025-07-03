package com.locauto.model;

import java.util.Date;

public class Reservation {

    private int id;
    private Client client;
    private Vehicule vehicule;
    private String client1;
    private String vehicule1;
    private Date date_retrait;
    private Date date_retour;
    private String statut;

    public Reservation(int id, Client client, Vehicule vehicule, Date date_retrait, Date date_retour, String statut) {
        this.id = id;
        this.client = client;
        this.vehicule = vehicule;
        this.date_retrait = date_retrait;
        this.date_retour = date_retour;
        this.statut = statut;
    }

    public Reservation(Client client, Vehicule vehicule, Date date_retrait, Date date_retour, String statut) {
        this.client = client;
        this.vehicule = vehicule;
        this.date_retrait = date_retrait;
        this.date_retour = date_retour;
        this.statut = statut;
    }
/*
    public Reservation(int id, String client1, String vehicule1, Date date_retrait, Date date_retour, String statut) {
        this.id = id;
        this.client1 = client1;
        this.vehicule1 = vehicule1;
        this.date_retrait = date_retrait;
        this.date_retour = date_retour;
        this.statut = statut;
    }

    public Reservation(String client1, String vehicule1, Date date_retrait, Date date_retour, String statut) {
        this.client1 = client1;
        this.vehicule1 = vehicule1;
        this.date_retrait = date_retrait;
        this.date_retour = date_retour;
        this.statut = statut;
    }*/


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public Date getDate_retrait() {
        return date_retrait;
    }

    public void setDate_retrait(Date date_retrait) {
        this.date_retrait = date_retrait;
    }

    public Date getDate_retour() {
        return date_retour;
    }

    public void setDate_retour(Date date_retour) {
        this.date_retour = date_retour;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getClient1() {
        return client1;
    }

    public void setClient1(String client1) {
        this.client1 = client1;
    }

    public String getVehicule1() {
        return vehicule1;
    }

    public void setVehicule1(String vehicule1) {
        this.vehicule1 = vehicule1;
    }
}
