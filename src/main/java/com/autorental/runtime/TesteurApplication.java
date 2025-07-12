package com.autorental.runtime;

import com.autorental.exceptions.DAOException;
import com.autorental.model.Vehicule;

public class TesteurApplication {
    public static void main(String[] args) throws DAOException {
        Testeur testeur = new Testeur();

        Vehicule vehicule = new Vehicule("Jeep", "AMG", 250000, true, "A-99C5L6");
        Vehicule vehicule1 = new Vehicule("Jeep", "Wrangler", 300000, true, "A-65C5L34");
        Vehicule vehicule2 = new Vehicule("Range Rover", "Velar", 325000, true, "BM-78D9L6");
        Vehicule vehicule3 = new Vehicule("Ford", "Escape", 400025, false, "A-5DF75L6");
        Vehicule vehicule4 = new Vehicule("Toyota", "Prado", 250000, true, "A-21QD96");

        testeur.ajouterObject(vehicule, Vehicule.class);
        testeur.ajouterObject(vehicule1, Vehicule.class);
        testeur.ajouterObject(vehicule2, Vehicule.class);
        testeur.ajouterObject(vehicule3, Vehicule.class);
        testeur.ajouterObject(vehicule4, Vehicule.class);

    }
}
