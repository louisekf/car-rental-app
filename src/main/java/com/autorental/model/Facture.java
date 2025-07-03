package com.locauto.model;

public class Facture {
    private int id;
    private String nom;
    private Reservation reservation;
    private String reservation1;

    public Facture(int id, String nom, Reservation reservation) {
        this.id = id;
        this.nom = nom;
        this.reservation = reservation;
    }

    public Facture(String nom, Reservation reservation) {
        this.nom = nom;
        this.reservation = reservation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getReservation1() {
        return reservation1;
    }

    public void setReservation1(String reservation1) {
        this.reservation1 = reservation1;
    }
}
