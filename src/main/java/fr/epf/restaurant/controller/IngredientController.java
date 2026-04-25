package fr.epf.restaurant.controller;

import fr.epf.restaurant.dao.IngredientDao;
import fr.epf.restaurant.dto.AlerteStockDto;
import fr.epf.restaurant.dto.RecommandationsDto;
import fr.epf.restaurant.exception.ResourceNotFoundException;
import fr.epf.restaurant.service.StockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {

    private final IngredientDao ingredientDao;
    private final StockService stockService;

    public IngredientController(IngredientDao ingredientDao, StockService stockService) {
        this.ingredientDao = ingredientDao;
        this.stockService = stockService;
    }

    @GetMapping
    public List<Map<String, Object>> getAll() {
        return ingredientDao.findAll();
    }

    @GetMapping("/alertes")
    public List<AlerteStockDto> getAlertes() {
        return stockService.getAlertes();
    }

    @GetMapping("/{id}/recommandation")
    public RecommandationsDto getRecommandation(@PathVariable Long id) {
        Map<String, Object> ing = ingredientDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient", id));
        Map<String, Object> meilleur = ingredientDao.findMeilleurFournisseur(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur pour ingredient", id));
        double stock = ((Number) ing.get("stockActuel")).doubleValue();
        double seuil = ((Number) ing.get("seuilAlerte")).doubleValue();
        int qte = (seuil > stock) ? (int) (2 * (seuil - stock)) : (int) seuil;
        RecommandationsDto dto = new RecommandationsDto();
        dto.setIngredientId(id);
        dto.setIngredientNom((String) ing.get("nom"));
        dto.setUnite((String) ing.get("unite"));
        dto.setStockActuel((int) stock);
        dto.setSeuilAlerte((int) seuil);
        dto.setFournisseurId(((Number) meilleur.get("fournisseurId")).longValue());
        dto.setFournisseurNom((String) meilleur.get("fournisseurNom"));
        dto.setPrixUnitaire(((Number) meilleur.get("prixUnitaire")).doubleValue());
        dto.setQuantiteRecommandee(qte);
        return dto;
    }

    @GetMapping("/{id}/prix")
    public List<Map<String, Object>> getPrix(@PathVariable Long id) {
        ingredientDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient", id));
        return ingredientDao.findPrixParFournisseur(id);
    }
}
