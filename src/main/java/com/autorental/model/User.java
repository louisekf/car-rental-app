package com.autorental.model;

import jakarta.persistence.*;

@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String prenom;
    private String nom;
    private String role;
    private String login;
    private String password;

    public User(){}

    public User(String prenom, String nom, String role, String login, String password) {
        this.prenom = prenom;
        this.nom = nom;
        this.role = role;
        this.login = login;
        this.password = password;
    }

    public User(String nom, String prenom, String role, String login, String password, int id) {
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
        this.login = login;
        this.password = password;
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
