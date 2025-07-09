package com.autorental.interfaces;

import com.autorental.dao.IDao;
import com.autorental.model.Facture;

public interface FactureFactory {
    IDao<Facture> getFactureDao(Class<? extends IDao<Facture>> daoFacture);
}
