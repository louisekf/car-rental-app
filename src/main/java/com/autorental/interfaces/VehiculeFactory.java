package com.autorental.interfaces;

import com.autorental.dao.IDao;
import com.autorental.model.Vehicule;

public interface VehiculeFactory {
    IDao<Vehicule> getVehiculeFactory(Class<? extends IDao<Vehicule>> daoVehicule);
}
