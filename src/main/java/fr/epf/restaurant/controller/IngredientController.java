package fr.epf.restaurant.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.epf.restaurant.dto.IngredientDto;
import fr.epf.restaurant.dto.RecommandationsDto;
import fr.epf.restaurant.model.Ingredient;
import fr.epf.restaurant.service.IngredientService;
@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public List<Ingredient> getAll(){
        return ingredientService.getAll();
    }

    @GetMapping("/alertes")
    public List<Ingredient> getByAlerte(){
        return ingredientService.getByAlerte();
    }

    @GetMapping("/{id}/prix")
    public List<IngredientDto> getPrixById(@PathVariable long id){
        return ingredientService.getPrixById(id);
    }

    @GetMapping("/{id}/recommandation")
    public RecommandationsDto getRecommandationById(@PathVariable long id){
        return ingredientService.getRecommandationsById(id);
    }


}
