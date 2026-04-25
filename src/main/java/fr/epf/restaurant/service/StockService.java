package fr.epf.restaurant.service;

import fr.epf.restaurant.dao.IngredientDao;
import fr.epf.restaurant.dto.AlerteStockDto;
import fr.epf.restaurant.exception.ResourceNotFoundException;
import fr.epf.restaurant.exception.StockInsuffisantException;
import org.springframework.stereotype.Service;
 
import java.util.List;
import java.util.Map;
 
@Service
public class StockService {
 
    private final IngredientDao ingredientDao;
 
    public StockService(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }
 
    public void consommerStock(Long ingredientId, double quantite) {
        Map<String, Object> ingredient = ingredientDao.findById(ingredientId)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient", ingredientId));
        double stockActuel = toDouble(ingredient.get("stockActuel"));
        if (stockActuel < quantite) {
            throw new StockInsuffisantException(
                    (String) ingredient.get("nom"), (int) stockActuel, (int) quantite);
        }
        ingredientDao.updateStock(ingredientId, stockActuel - quantite);
    }
 
    public void restituerStock(Long ingredientId, double quantite) {
        Map<String, Object> ingredient = ingredientDao.findById(ingredientId)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient", ingredientId));
        double stockActuel = toDouble(ingredient.get("stockActuel"));
        ingredientDao.updateStock(ingredientId, stockActuel + quantite);
    }
 
    public List<AlerteStockDto> getAlertes() {
        return ingredientDao.findSousAlerte().stream().map(row -> {
            double stock = toDouble(row.get("stockActuel"));
            double seuil = toDouble(row.get("seuilAlerte"));
            int qte = (int) (2 * (seuil - stock));
            return new AlerteStockDto(
                    toLong(row.get("id")),
                    (String) row.get("nom"),
                    (String) row.get("unite"),
                    (int) stock,
                    (int) seuil,
                    qte);
        }).toList();
    }
 
    private static double toDouble(Object o) {
        return o == null ? 0.0 : ((Number) o).doubleValue();
    }
 
    private static Long toLong(Object o) {
        return o == null ? null : ((Number) o).longValue();
    }
}
