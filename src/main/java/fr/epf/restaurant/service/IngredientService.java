package fr.epf.restaurant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.epf.restaurant.dao.IngredientDao;
import fr.epf.restaurant.dto.IngredientDto;
import fr.epf.restaurant.dto.RecommandationsDto;
import fr.epf.restaurant.model.Ingredient;
@Service
public class IngredientService {
    private final IngredientDao ingredientDao;

    public IngredientService(IngredientDao ingredientDao){
        this.ingredientDao=ingredientDao;
    }

    public List<Ingredient> getAll(){
        return ingredientDao.findAll();
    }

    public List<Ingredient> getByAlerte(){
        return ingredientDao.findByAlert();
    }

    public List<IngredientDto> getPrixById(long id){
        return ingredientDao.findPrixById(id);
    }

    public RecommandationsDto getRecommandationsById(long id){
        return ingredientDao.findRecommandation(id);
    }

}
