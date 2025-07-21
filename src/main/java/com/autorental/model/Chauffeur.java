package com.autorental.model;

import jakarta.persistence.*;

@Entity
@Table(name = "chauffeurs")
public class Chauffeur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String prenom;
    private boolean dispo;

    public Chauffeur(){}

    public Chauffeur(int id, String nom, String prenom, boolean dispo) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dispo = dispo;
    }

    public Chauffeur(String nom, String prenom, boolean dispo) {
        this.nom = nom;
        this.prenom = prenom;
        this.dispo = dispo;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public boolean getDispo() {
        return dispo;
    }
    public void setDispo(boolean dispo) {
        this.dispo = dispo;
    }

    @Override
    public String toString() {
        return prenom + " " + nom;
    }

}
