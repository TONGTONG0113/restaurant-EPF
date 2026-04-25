package fr.epf.restaurant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.epf.restaurant.dao.ClientDao;
import fr.epf.restaurant.dao.CommandeClientDao;
import fr.epf.restaurant.dao.IngredientDao;
import fr.epf.restaurant.dao.PlatDao;
import fr.epf.restaurant.dto.CreerCommandeClientRequest;
import fr.epf.restaurant.exception.ResourceNotFoundException;
import fr.epf.restaurant.exception.StatutInvalideException;
import fr.epf.restaurant.exception.StockInsuffisantException;
import fr.epf.restaurant.service.CommandeClientService;
import fr.epf.restaurant.service.StockService;

public class Commandeclientservicetest {
    private Map<String, Object> commande(Long id, String statut) {
        Map<String, Object> c = new LinkedHashMap<>();
        c.put("id", id);
        c.put("statut", statut);
        c.put("client", Map.of("id", 1L, "nom", "Dupont"));
        c.put("lignes", new ArrayList<>());
        return c;
    }
 
    private Map<String, Object> ingredient(Long id, String nom, double stock, double seuil) {
        return Map.of("id", id, "nom", nom, "unite", "g",
                "stockActuel", stock, "seuilAlerte", seuil);
    }
 
    private CreerCommandeClientRequest buildRequest(Long clientId, Long platId, int qte) {
        CreerCommandeClientRequest req = new CreerCommandeClientRequest();
        req.setClientId(clientId);
        CreerCommandeClientRequest.LigneCommande ligne = new CreerCommandeClientRequest.LigneCommande();
        ligne.setPlatId(platId);
        ligne.setQuantite(qte);
        req.setLignes(List.of(ligne));
        return req;
    }
 
    private CommandeClientService buildService(
            ClientDao clientDao,
            CommandeClientDao commandeDao,
            PlatDao platDao,
            IngredientDao ingredientDao,
            StockService stockService) {
        return new CommandeClientService(commandeDao, clientDao, platDao, ingredientDao, stockService);
    }
 
 
    @Test
    @DisplayName("creer : client inexistant → ResourceNotFoundException")
    void creer_clientInexistant() {
        ClientDao clientDao = new ClientDao(null) {
            @Override public Optional<Map<String, Object>> findById(Long id) { return Optional.empty(); }
        };
        CommandeClientService service = buildService(clientDao,
                new CommandeClientDao(null), new PlatDao(null),
                new IngredientDao(null), new StockService(new IngredientDao(null)));
 
        assertThrows(ResourceNotFoundException.class,
                () -> service.creer(buildRequest(999L, 1L, 2)));
    }
 
    @Test
    @DisplayName("creer : plat inexistant → ResourceNotFoundException")
    void creer_platInexistant() {
        ClientDao clientDao = new ClientDao(null) {
            @Override public Optional<Map<String, Object>> findById(Long id) {
                return Optional.of(Map.of("id", 1L));
            }
        };
        CommandeClientDao commandeDao = new CommandeClientDao(null) {
            @Override public Map<String, Object> create(Long clientId) {
                return Map.of("id", 10L, "statut", "EN_ATTENTE", "client",
                        Map.of("id", 1L), "lignes", new ArrayList<>());
            }
        };
        PlatDao platDao = new PlatDao(null) {
            @Override public Optional<Map<String, Object>> findById(Long id) { return Optional.empty(); }
        };
        CommandeClientService service = buildService(clientDao, commandeDao, platDao,
                new IngredientDao(null), new StockService(new IngredientDao(null)));
 
        assertThrows(ResourceNotFoundException.class,
                () -> service.creer(buildRequest(1L, 888L, 1)));
    }

 
    @Test
    @DisplayName("preparer : stock insuffisant → StockInsuffisantException")
    void preparer_stockInsuffisant() {
        CommandeClientDao commandeDao = new CommandeClientDao(null) {
            @Override public Optional<Map<String, Object>> findById(Long id) {
                return Optional.of(commande(2L, "EN_ATTENTE"));
            }
            @Override public List<Map<String, Object>> findBesoinsIngredients(Long id) {
                return List.of(Map.of("ingredientId", 7L, "quantitePlat", 1, "quantiteRequise", 10.0));
            }
        };
        IngredientDao ingredientDao = new IngredientDao(null) {
            @Override public Optional<Map<String, Object>> findById(Long id) {
                return Optional.of(ingredient(7L, "Beurre", 3.0, 5.0));
            }
        };
        StockService stockService = new StockService(ingredientDao);
        CommandeClientService service = buildService(new ClientDao(null), commandeDao,
                new PlatDao(null), ingredientDao, stockService);
 
        assertThrows(StockInsuffisantException.class,
                () -> service.passerEnPreparation(2L));
    }
 
    @Test
    @DisplayName("preparer : statut invalide → StatutInvalideException")
    void preparer_statutInvalide() {
        CommandeClientDao commandeDao = new CommandeClientDao(null) {
            @Override public Optional<Map<String, Object>> findById(Long id) {
                return Optional.of(commande(3L, "EN_PREPARATION"));
            }
        };
        CommandeClientService service = buildService(new ClientDao(null), commandeDao,
                new PlatDao(null), new IngredientDao(null),
                new StockService(new IngredientDao(null)));
 
        assertThrows(StatutInvalideException.class,
                () -> service.passerEnPreparation(3L));
    }
 
 
    @Test
    @DisplayName("servir : statut EN_ATTENTE → StatutInvalideException")
    void servir_statutInvalide() {
        CommandeClientDao commandeDao = new CommandeClientDao(null) {
            @Override public Optional<Map<String, Object>> findById(Long id) {
                return Optional.of(commande(5L, "EN_ATTENTE"));
            }
        };
        CommandeClientService service = buildService(new ClientDao(null), commandeDao,
                new PlatDao(null), new IngredientDao(null),
                new StockService(new IngredientDao(null)));
 
        assertThrows(StatutInvalideException.class, () -> service.marquerServie(5L));
    }
 
    @Test
    @DisplayName("servir : EN_PREPARATION → SERVIE")
    void servir_valide() {
        String[] statutMaj = {""};
        CommandeClientDao commandeDao = new CommandeClientDao(null) {
            private int call = 0;
            @Override public Optional<Map<String, Object>> findById(Long id) {
                return Optional.of(commande(4L, call++ == 0 ? "EN_PREPARATION" : "SERVIE"));
            }
            @Override public void updateStatut(Long id, String statut) { statutMaj[0] = statut; }
        };
        CommandeClientService service = buildService(new ClientDao(null), commandeDao,
                new PlatDao(null), new IngredientDao(null),
                new StockService(new IngredientDao(null)));
 
        Map<String, Object> result = service.marquerServie(4L);
        assertEquals("SERVIE", statutMaj[0]);
        assertEquals("SERVIE", result.get("statut"));
    }
 
    @Test
    @DisplayName("supprimer : commande inexistante → ResourceNotFoundException")
    void supprimer_inexistant() {
        CommandeClientDao commandeDao = new CommandeClientDao(null) {
            @Override public Optional<Map<String, Object>> findById(Long id) {
                return Optional.empty();
            }
        };
        CommandeClientService service = buildService(new ClientDao(null), commandeDao,
                new PlatDao(null), new IngredientDao(null),
                new StockService(new IngredientDao(null)));
 
        assertThrows(ResourceNotFoundException.class, () -> service.supprimer(999L));
    }

}
