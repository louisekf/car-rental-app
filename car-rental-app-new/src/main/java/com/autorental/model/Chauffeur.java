package com.autorental.model;

import jakarta.persistence.*;

@Entity
@Table(name = "chauffeurs")
public class Chauffeur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;


    @Column(nullable = false)
    private String dispo;

    public Chauffeur() {}

    public Chauffeur(String nom, String prenom, String dispo) {
        this.nom = nom;
        this.prenom = prenom;
        this.dispo = dispo;
    }

    public Chauffeur(int id, String nom, String prenom, String dispo) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dispo = dispo;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }



    public String getDispo() { return dispo; }
    public void setDispo(String dispo) { this.dispo = dispo; }

    @Override
    public String toString() {
        return nom + " " + prenom;
    }

    public String getNomComplet() {
        return nom + " " + prenom;
    }
}
