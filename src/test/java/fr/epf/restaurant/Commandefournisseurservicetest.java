package fr.epf.restaurant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.epf.restaurant.dao.CommandeFournisseurDao;
import fr.epf.restaurant.dao.FournisseurDao;
import fr.epf.restaurant.dao.IngredientDao;
import fr.epf.restaurant.dto.CreerCommandeFournisseurRequest;
import fr.epf.restaurant.exception.ResourceNotFoundException;
import fr.epf.restaurant.exception.StatutInvalideException;
import fr.epf.restaurant.service.CommandeFournisseurService;
import fr.epf.restaurant.service.StockService;

public class Commandefournisseurservicetest {
    private Map<String, Object> commande(Long id, String statut) {
        Map<String, Object> c = new LinkedHashMap<>();
        c.put("id", id);
        c.put("statut", statut);
        c.put("fournisseur", Map.of("id", 1L, "nom", "Metro"));
        c.put("lignes", new ArrayList<>());
        return c;
    }
 
    private CommandeFournisseurService buildService(
            CommandeFournisseurDao dao, FournisseurDao fDao,
            IngredientDao iDao, StockService stock) {
        return new CommandeFournisseurService(dao, fDao, iDao, stock);
    }
 
    @Test
    @DisplayName("creer : fournisseur inexistant → ResourceNotFoundException")
    void creer_fournisseurInexistant() {
        FournisseurDao fDao = new FournisseurDao(null) {
            @Override public Optional<Map<String, Object>> findById(Long id) { return Optional.empty(); }
        };
        CreerCommandeFournisseurRequest req = new CreerCommandeFournisseurRequest();
        req.setFournisseurId(999L);
        req.setLignes(List.of());
        CommandeFournisseurService service = buildService(
                new CommandeFournisseurDao(null), fDao,
                new IngredientDao(null), new StockService(new IngredientDao(null)));
 
        assertThrows(ResourceNotFoundException.class, () -> service.creer(req));
    }
 
    @Test
    @DisplayName("envoyer : EN_ATTENTE → ENVOYEE")
    void envoyer_valide() {
        String[] statutMaj = {""};
        CommandeFournisseurDao dao = new CommandeFournisseurDao(null) {
            private int call = 0;
            @Override public Optional<Map<String, Object>> findById(Long id) {
                return Optional.of(commande(1L, call++ == 0 ? "EN_ATTENTE" : "ENVOYEE"));
            }
            @Override public void updateStatut(Long id, String s) { statutMaj[0] = s; }
        };
        CommandeFournisseurService service = buildService(dao, new FournisseurDao(null),
                new IngredientDao(null), new StockService(new IngredientDao(null)));
        Map<String, Object> result = service.envoyer(1L);
        assertEquals("ENVOYEE", statutMaj[0]);
        assertEquals("ENVOYEE", result.get("statut"));
    }
 
    @Test
    @DisplayName("envoyer : déjà ENVOYEE → StatutInvalideException")
    void envoyer_statutInvalide() {
        CommandeFournisseurDao dao = new CommandeFournisseurDao(null) {
            @Override public Optional<Map<String, Object>> findById(Long id) {
                return Optional.of(commande(2L, "ENVOYEE"));
            }
        };
        CommandeFournisseurService service = buildService(dao, new FournisseurDao(null),
                new IngredientDao(null), new StockService(new IngredientDao(null)));
        assertThrows(StatutInvalideException.class, () -> service.envoyer(2L));
    }
 
    @Test
    @DisplayName("recevoir : ENVOYEE → RECUE + stock réapprovisionné")
    void recevoir_valide_stockMisAJour() {
        List<Long> stocksRestitues = new ArrayList<>();
        List<Double> quantitesRestitues = new ArrayList<>();
 
        CommandeFournisseurDao dao = new CommandeFournisseurDao(null) {
            private int call = 0;
            @Override public Optional<Map<String, Object>> findById(Long id) {
                return Optional.of(commande(3L, call++ == 0 ? "ENVOYEE" : "RECUE"));
            }
            @Override public List<Map<String, Object>> findLignes(Long id) {
                return List.of(
                        Map.of("ingredientId", 10L, "quantite", 50.0),
                        Map.of("ingredientId", 11L, "quantite", 20.0));
            }
            @Override public void updateStatut(Long id, String s) {}
        };
        IngredientDao iDao = new IngredientDao(null) {
            @Override public Optional<Map<String, Object>> findById(Long id) {
                return Optional.of(Map.of("id", id, "nom", "Test", "unite", "g",
                        "stockActuel", 100.0, "seuilAlerte", 10.0));
            }
            @Override public void updateStock(Long id, double s) {
                stocksRestitues.add(id);
                quantitesRestitues.add(s);
            }
        };
        StockService stockService = new StockService(iDao);
        CommandeFournisseurService service = buildService(dao, new FournisseurDao(null),
                iDao, stockService);
 
        Map<String, Object> result = service.recevoir(3L);
 
        assertTrue(stocksRestitues.contains(10L));
        assertTrue(stocksRestitues.contains(11L));
        assertEquals("RECUE", result.get("statut"));
    }
 
    @Test
    @DisplayName("recevoir : EN_ATTENTE → StatutInvalideException")
    void recevoir_statutInvalide() {
        CommandeFournisseurDao dao = new CommandeFournisseurDao(null) {
            @Override public Optional<Map<String, Object>> findById(Long id) {
                return Optional.of(commande(4L, "EN_ATTENTE"));
            }
        };
        CommandeFournisseurService service = buildService(dao, new FournisseurDao(null),
                new IngredientDao(null), new StockService(new IngredientDao(null)));
        assertThrows(StatutInvalideException.class, () -> service.recevoir(4L));
    }

}
