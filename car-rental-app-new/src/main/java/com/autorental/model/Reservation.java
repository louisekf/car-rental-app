package com.autorental.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "chauffeur_id")
    private Chauffeur chauffeur;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule;

    @Column(name = "date_retrait")
    private LocalDateTime date_retrait;

    @Column(name = "date_retour")
    private LocalDateTime date_retour;

    @Column(nullable = true)
    private String statut;

    public Reservation() {}

    public Reservation(Chauffeur chauffeur, Client client, Vehicule vehicule,
                       LocalDateTime date_retrait, LocalDateTime date_retour, String statut) {
        this.chauffeur = chauffeur;
        this.client = client;
        this.vehicule = vehicule;
        this.date_retrait = date_retrait;
        this.date_retour = date_retour;
        this.statut = statut;
    }

    // Getters et Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Chauffeur getChauffeur() { return chauffeur; }
    public void setChauffeur(Chauffeur chauffeur) { this.chauffeur = chauffeur; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public Vehicule getVehicule() { return vehicule; }
    public void setVehicule(Vehicule vehicule) { this.vehicule = vehicule; }

    public LocalDateTime getDate_retrait() { return date_retrait; }
    public void setDate_retrait(LocalDateTime date_retrait) { this.date_retrait = date_retrait; }

    public LocalDateTime getDate_retour() { return date_retour; }
    public void setDate_retour(LocalDateTime date_retour) { this.date_retour = date_retour; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}
