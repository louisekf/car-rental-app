package com.autorental.factory;

import com.autorental.dao.IDao;
import com.autorental.dao.impl.*;
import com.autorental.exceptions.DAOException;
import com.autorental.interfaces.*;
import com.autorental.model.*;

public class HibernateFactoryImpl extends AbstractFactory {
    @Override
    public UserFactory getUserFactory() {
        return new UserFactory() {
            @Override
            public IDao<User> getUserDao(Class<? extends IDao<User>> daoUser) {
                if (daoUser == HibernateUserDaoImpl.class) {
                    return new HibernateUserDaoImpl();
                }
                return null;
            }
        };
    }

    @Override
    public ClientFactory getClientFactory() {
        return new ClientFactory(){
            @Override
            public IDao<Client> getClientDao(Class<? extends IDao<Client>> daoClient) {
                if (daoClient == HibernateClientDaoImpl.class) {
                    return new HibernateClientDaoImpl();
                }
                return null;
            }
        };
    }

    @Override
    public ChauffeurFactory getChauffeurFactory() {
        return new ChauffeurFactory(){
            @Override
            public IDao<Chauffeur> getChauffeurDao(Class<? extends IDao<Chauffeur>> daoChauffeur) {
                if (daoChauffeur == HibernateChauffeurDaoImpl.class) {
                    return new HibernateChauffeurDaoImpl();
                }
                return null;
            }
        };
    }

    @Override
    public FactureFactory getFactureFactory() {
        return new FactureFactory() {
            @Override
            public IDao<Facture> getFactureDao(Class<? extends IDao<Facture>> daoFacture) {
                if (daoFacture == HibernateFactureDaoImpl.class) {
                    return new HibernateFactureDaoImpl();
                }
                return null;
            }
        };
    }

    @Override
    public ReservationFactory getReservationFactory() {
        return new ReservationFactory(){
            @Override
            public IDao<Reservation> getReservationDao(Class<? extends IDao<Reservation>> daoReservation) {
                if (daoReservation == HibernateReservationDaoImpl.class) {
                    return new HibernateReservationDaoImpl();
                }
                return null;
            }
        };
    }

    @Override
    public VehiculeFactory getVehiculeFactory() {
        return new VehiculeFactory() {
            @Override
            public IDao<Vehicule> getVehiculeFactory(Class<? extends IDao<Vehicule>> daoVehicule) {
                if (daoVehicule == HibernateVehiculeDaoImpl.class) {
                    return new HibernateVehiculeDaoImpl();
                }
                return null;
            }
        };
    }

    @Override
    public HibernateFactory getHibernateFactory() {
        return new HibernateFactory(){
            @Override
            public <T> HibernateObjectDaoImpl<T> getHibernateObjectDaoImpl(Class<T> entityClass) throws DAOException {
                try {
                    return new HibernateObjectDaoImpl<>(entityClass);
                } catch (Exception e) {
                    throw new DAOException("Error creating HibernateObjectDaoImpl for class: " + entityClass.getName() + ": " + e.getMessage());
                }
            }
        };
    }
}
