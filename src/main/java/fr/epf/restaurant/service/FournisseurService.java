package fr.epf.restaurant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.epf.restaurant.dao.FournisseurDao;
import fr.epf.restaurant.dto.FournisseurDto;
import fr.epf.restaurant.model.Fournisseur;

@Service
public class FournisseurService {
    private final FournisseurDao fournisseurDao;

    public FournisseurService(FournisseurDao fournisseurDao) {
        this.fournisseurDao = fournisseurDao;
    }
    
    public List<Fournisseur> getAll(){
       return fournisseurDao.findAll();
    }

    public List<FournisseurDto> getPrixById(long id){
        return fournisseurDao.findPrixById(id);
    }


}
