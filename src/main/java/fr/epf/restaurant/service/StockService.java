package fr.epf.restaurant.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import fr.epf.restaurant.dao.IngredientDao;
import fr.epf.restaurant.dto.AlerteStockDto;
import fr.epf.restaurant.exception.ResourceNotFoundException;
import fr.epf.restaurant.exception.StockInsuffisantException;

@Service
public class StockService {
    private final IngredientDao ingredientDao;
 
    public StockService(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }
 
    public void consommerStock(Long ingredientId, double quantite) {
        Map<String, Object> ingredient = ingredientDao.findById(ingredientId)
                .orElseThrow(() -> new ResourceNotFoundException("Ingrédient", ingredientId));
        double stockActuel = toDouble(ingredient.get("stockActuel"));
        if (stockActuel < quantite) {
            throw new StockInsuffisantException(
                    (String) ingredient.get("nom"), (int) stockActuel, (int) quantite);
        }
        ingredientDao.updateStock(ingredientId, stockActuel - quantite);
    }
 
    public void restituerStock(Long ingredientId, double quantite) {
        Map<String, Object> ingredient = ingredientDao.findById(ingredientId)
                .orElseThrow(() -> new ResourceNotFoundException("Ingrédient", ingredientId));
        double stockActuel = toDouble(ingredient.get("stockActuel"));
        ingredientDao.updateStock(ingredientId, stockActuel + quantite);
    }
 
    public List<AlerteStockDto> getAlertes() {
        return ingredientDao.findSousAlerte().stream().map(row -> {
            double stock = toDouble(row.get("stockActuel"));
            double seuil = toDouble(row.get("seuilAlerte"));
            int qteACommander = (int) (2 * (seuil - stock));
            return new AlerteStockDto(
                    toLong(row.get("id")),
                    (String) row.get("nom"),
                    (String) row.get("unite"),
                    (int) stock,
                    (int) seuil,
                    qteACommander
            );
        }).toList();
    }
 
    private static double toDouble(Object o) {
        if (o == null) return 0.0;
        return ((Number) o).doubleValue();
    }
 
    private static Long toLong(Object o) {
        if (o == null) return null;
        return ((Number) o).longValue();
    }
}
