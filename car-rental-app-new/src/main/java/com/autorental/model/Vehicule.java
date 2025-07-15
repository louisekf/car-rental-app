package com.autorental.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicules")
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String marque;

    @Column(nullable = false)
    private String modele;

    @Column(nullable = false)
    private double tarif;

    @Column(nullable = false)
    private boolean dispo;

    @Column(nullable = false, unique = true)
    private String immatriculation;

    public Vehicule() {
        // constructeur sans arguments requis par JPA
    }

    public Vehicule(int id, String marque, String modele, double tarif, boolean dispo, String immatriculation) {
        this.id = id;
        this.marque = marque;
        this.modele = modele;
        this.tarif = tarif;
        this.dispo = dispo;
        this.immatriculation = immatriculation;
    }

    public Vehicule(String marque, String modele, double tarif, boolean dispo, String immatriculation) {
        this.marque = marque;
        this.modele = modele;
        this.tarif = tarif;
        this.dispo = dispo;
        this.immatriculation = immatriculation;
    }

    // Getters et setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public boolean isDispo() {
        return dispo;
    }

    public void setDispo(boolean dispo) {
        this.dispo = dispo;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public double getTarif() {
        return tarif;
    }

    public void setTarif(double tarif) {
        this.tarif = tarif;
    }

    @Override
    public String toString() {
        return marque + " " + modele + " (" + immatriculation + ")";
    }

    public String getMarqueModele() {
        return marque + " "+modele;
    }
}
