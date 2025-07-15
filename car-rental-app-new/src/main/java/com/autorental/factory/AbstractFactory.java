package com.autorental.factory;

import com.autorental.interfaces.*;

public abstract class AbstractFactory {
    public abstract UserFactory getUserFactory();
    public abstract ClientFactory getClientFactory();
    public abstract ChauffeurFactory getChauffeurFactory();
    public abstract FactureFactory getFactureFactory();
    public abstract ReservationFactory getReservationFactory();
    public abstract VehiculeFactory getVehiculeFactory();
    public abstract HibernateFactory getHibernateFactory();
}
