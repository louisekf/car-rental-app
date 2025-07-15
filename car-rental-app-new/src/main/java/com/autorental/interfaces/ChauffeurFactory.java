package com.autorental.interfaces;

import com.autorental.dao.IDao;
import com.autorental.model.Chauffeur;
import com.autorental.model.User;

public interface ChauffeurFactory {
    IDao<Chauffeur> getChauffeurDao(Class<? extends IDao<Chauffeur>> daoChauffeur);
}
