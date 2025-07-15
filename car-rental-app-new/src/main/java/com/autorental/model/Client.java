package com.autorental.model;

import jakarta.persistence.*;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String tel;
    private String adresse;
    private int pts_fidelite;
    private double solde;

    public Client(){}

    public Client(int id, String nom, String prenom, String email, String tel, String adresse, int pts_fidelite, double solde) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.tel = tel;
        this.adresse = adresse;
        this.pts_fidelite = pts_fidelite;
        this.solde = solde;
    }

    public Client(String nom, String prenom, String email, String tel, String adresse) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.tel = tel;
        this.adresse = adresse;
        setPts_fidelite(0);
        setSolde(0.0);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getPts_fidelite() {
        return pts_fidelite;
    }

    public void setPts_fidelite(int pts_fidelite) {
        this.pts_fidelite = pts_fidelite;
    }

    public double getSolde() { return solde; }

    public void setSolde(double solde) { this.solde = solde; }

    public String getNomComplet() {
        return prenom + " " + nom;
    }
}
