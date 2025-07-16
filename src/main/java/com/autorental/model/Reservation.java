package com.autorental.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule;

    @ManyToOne
    @JoinColumn(name = "chauffeur_id", nullable = true)
    private Chauffeur chauffeur;

    private Date date_retrait;

    private Date date_retour;

    private String statut;

    public Reservation(){}

    public Reservation(int id, Client client, Vehicule vehicule, Chauffeur chauffeur, Date date_retrait, Date date_retour, String statut) {
        this.id = id;
        this.client = client;
        this.vehicule = vehicule;
        this.chauffeur = chauffeur;
        this.date_retrait = date_retrait;
        this.date_retour = date_retour;
        this.statut = statut;
    }

    public Reservation(Client client, Vehicule vehicule,Chauffeur chauffeur, Date date_retrait, Date date_retour, String statut) {
        this.client = client;
        this.vehicule = vehicule;
        this.chauffeur = chauffeur;
        this.date_retrait = date_retrait;
        this.date_retour = date_retour;
        this.statut = statut;
    }

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

    public Chauffeur getChauffeur() {
        return chauffeur;
    }
    public void setChauffeur(Chauffeur chauffeur) {
        this.chauffeur = chauffeur;
    }

}
