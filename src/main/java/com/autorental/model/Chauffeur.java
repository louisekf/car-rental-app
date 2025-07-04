package com.locauto.model;

public class Chauffeur {
    private int id;
    private String nom;
    private String prenom;
    private String dispo;

    public Chauffeur(int id, String nom, String prenom, String dispo) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dispo = dispo;
    }

    public Chauffeur(String nom, String prenom, String dispo) {
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

    public String getDispo() {
        return dispo;
    }

    public void setDispo(String dispo) {
        this.dispo = dispo;
    }
}
