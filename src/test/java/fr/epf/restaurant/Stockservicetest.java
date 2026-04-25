package fr.epf.restaurant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.epf.restaurant.dao.IngredientDao;
import fr.epf.restaurant.dto.AlerteStockDto;
import fr.epf.restaurant.exception.ResourceNotFoundException;
import fr.epf.restaurant.exception.StockInsuffisantException;
import fr.epf.restaurant.service.StockService;


public class Stockservicetest {
    private Map<String, Object> ing(Long id, String nom, double stock, double seuil) {
        return Map.of("id", id, "nom", nom, "unite", "g",
                "stockActuel", stock, "seuilAlerte", seuil);
    }
 
    @Test
    @DisplayName("consommerStock : stock suffisant → décrémente correctement")
    void consommerStock_suffisant() {
        double[] stockMaj = {0};
        IngredientDao dao = new IngredientDao(null) {
            @Override public Optional<Map<String, Object>> findById(Long id) {
                return Optional.of(ing(1L, "Farine", 100.0, 20.0));
            }
            @Override public void updateStock(Long id, double s) { stockMaj[0] = s; }
        };
        StockService service = new StockService(dao);
        service.consommerStock(1L, 30.0);
        assertEquals(70.0, stockMaj[0]);
    }
 
    @Test
    @DisplayName("consommerStock : stock insuffisant → StockInsuffisantException")
    void consommerStock_insuffisant_throwsException() {
        IngredientDao dao = new IngredientDao(null) {
            @Override public Optional<Map<String, Object>> findById(Long id) {
                return Optional.of(ing(2L, "Beurre", 5.0, 10.0));
            }
        };
        StockService service = new StockService(dao);
        StockInsuffisantException ex = assertThrows(StockInsuffisantException.class,
                () -> service.consommerStock(2L, 10.0));
        assertTrue(ex.getMessage().contains("Beurre"));
    }
 
    @Test
    @DisplayName("consommerStock : ingrédient inexistant → ResourceNotFoundException")
    void consommerStock_ingredientInexistant() {
        IngredientDao dao = new IngredientDao(null) {
            @Override public Optional<Map<String, Object>> findById(Long id) {
                return Optional.empty();
            }
        };
        StockService service = new StockService(dao);
        assertThrows(ResourceNotFoundException.class,
                () -> service.consommerStock(99L, 5.0));
    }
 
    @Test
    @DisplayName("restituerStock : incrémente le stock correctement")
    void restituerStock_incrementeStock() {
        double[] stockMaj = {0};
        IngredientDao dao = new IngredientDao(null) {
            @Override public Optional<Map<String, Object>> findById(Long id) {
                return Optional.of(ing(1L, "Farine", 50.0, 20.0));
            }
            @Override public void updateStock(Long id, double s) { stockMaj[0] = s; }
        };
        StockService service = new StockService(dao);
        service.restituerStock(1L, 30.0);
        assertEquals(80.0, stockMaj[0]);
    }
 
    @Test
    @DisplayName("getAlertes : ingrédient sous le seuil → retourné avec bonne quantité")
    void getAlertes_ingredientSousSeuil_retourne() {
        IngredientDao dao = new IngredientDao(null) {
            @Override public List<Map<String, Object>> findSousAlerte() {
                return List.of(ing(3L, "Sel", 2.0, 10.0));
            }
        };
        StockService service = new StockService(dao);
        List<AlerteStockDto> alertes = service.getAlertes();
        assertEquals(1, alertes.size());
        assertEquals("Sel", alertes.get(0).getIngredientNom());
        assertEquals(16, alertes.get(0).getQuantiteACommander()); // 2 × (10-2)
    }
 
    @Test
    @DisplayName("getAlertes : aucun ingrédient en alerte → liste vide")
    void getAlertes_aucune_listeVide() {
        IngredientDao dao = new IngredientDao(null) {
            @Override public List<Map<String, Object>> findSousAlerte() {
                return List.of();
            }
        };
        StockService service = new StockService(dao);
        assertTrue(service.getAlertes().isEmpty());
    }

}
