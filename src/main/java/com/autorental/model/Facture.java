package com.autorental.model;

import jakarta.persistence.*;

@Entity
@Table(name = "factures")
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    public Facture(){}

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
}
