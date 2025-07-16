package com.autorental.runtime;

import com.autorental.exceptions.DAOException;
import com.autorental.model.Chauffeur;

public class TesteurApplication {
    public static void main(String[] args) throws DAOException {
        Testeur testeur = new Testeur();

        Chauffeur vehicule = new Chauffeur("Fall", "Maodo", true);
        Chauffeur vehicule1 = new Chauffeur("Pouye", "Ndiogou", false);
        Chauffeur vehicule2 = new Chauffeur("Drame", "Momodou", false);

        testeur.ajouterObject(vehicule, Chauffeur.class);
        testeur.ajouterObject(vehicule1, Chauffeur.class);
        testeur.ajouterObject(vehicule2, Chauffeur.class);

    }
}
