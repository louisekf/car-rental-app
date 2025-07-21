package com.autorental.factory;

import com.autorental.interfaces.*;

public class ConcreteFactory {
    public static <T> T getFactory(Class<T> factoryClass) {

        AbstractFactory factory = new HibernateFactoryImpl();

        //UserFactory
        if (factoryClass == UserFactory.class){
            return factoryClass.cast(factory.getUserFactory());
        }

        //ClientFactory
        if(factoryClass == ClientFactory.class){
            return factoryClass.cast(factory.getClientFactory());
        }

        //ChauffeurFactory
        if(factoryClass == ChauffeurFactory.class){
            return factoryClass.cast(factory.getChauffeurFactory());
        }

        //FactureFactory
        if(factoryClass == FactureFactory.class){
            return factoryClass.cast(factory.getFactureFactory());
        }

        //ReservationFactory
        if(factoryClass == ReservationFactory.class){
            return factoryClass.cast(factory.getReservationFactory());
        }

        //VehiculeFactory
        if(factoryClass == VehiculeFactory.class){
            return factoryClass.cast(factory.getVehiculeFactory());
        }

        //HibernateFactory
        if(factoryClass == HibernateFactory.class){
            return factoryClass.cast(factory.getHibernateFactory());
        }

        throw new IllegalArgumentException("Unknown factory class: " + factoryClass.getName());

    }
}
