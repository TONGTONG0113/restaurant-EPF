package fr.epf.restaurant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.epf.restaurant.dao.IngredientDao;
import fr.epf.restaurant.dao.PlatDao;
import fr.epf.restaurant.model.Ingredient;
import fr.epf.restaurant.model.Plat;
import fr.epf.restaurant.dto.PlatDto;
@Service
public class PlatService {
    private final PlatDao platDao;
    private final IngredientDao ingredientDao;
    
    
    public PlatService(PlatDao platDao,IngredientDao ingredientDao){
        this.platDao=platDao;   
        this.ingredientDao=ingredientDao;  
    }

    public List<Plat> getAllPlats(){
        return platDao.findAll();

    }

    public PlatDto getById(long id){
         Plat plat = platDao.findById(id);

         List<Ingredient> ingredients = ingredientDao.findAll();

        return new PlatDto(
            plat.getId(),
            plat.getNom(),
            plat.getDescription(),
            plat.getPrix(),
            ingredients
        );
    }  

    public void creatPlat(Plat plat){
        platDao.save(plat);
    }

    

}
