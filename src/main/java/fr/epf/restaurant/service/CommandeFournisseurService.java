package fr.epf.restaurant.service;

import fr.epf.restaurant.dao.CommandeFournisseurDao;
import fr.epf.restaurant.dao.FournisseurDao;
import fr.epf.restaurant.dao.IngredientDao;
import fr.epf.restaurant.dto.CreerCommandeFournisseurRequest;
import fr.epf.restaurant.exception.ResourceNotFoundException;
import fr.epf.restaurant.exception.StatutInvalideException;
import org.springframework.stereotype.Service;
 
import java.util.List;
import java.util.Map;
 
@Service
public class CommandeFournisseurService {
 
    private final CommandeFournisseurDao commandeFournisseurDao;
    private final FournisseurDao fournisseurDao;
    private final IngredientDao ingredientDao;
    private final StockService stockService;
 
    public CommandeFournisseurService(CommandeFournisseurDao commandeFournisseurDao,
            FournisseurDao fournisseurDao, IngredientDao ingredientDao,
            StockService stockService) {
        this.commandeFournisseurDao = commandeFournisseurDao;
        this.fournisseurDao = fournisseurDao;
        this.ingredientDao = ingredientDao;
        this.stockService = stockService;
    }
 
    public List<Map<String, Object>> findAll() {
        return commandeFournisseurDao.findAll();
    }
 
    public Map<String, Object> findById(Long id) {
        return commandeFournisseurDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande fournisseur", id));
    }
 
    public Map<String, Object> creer(CreerCommandeFournisseurRequest request) {
        fournisseurDao.findById(request.getFournisseurId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Fournisseur", request.getFournisseurId()));
        Map<String, Object> commande = commandeFournisseurDao.create(request.getFournisseurId());
        Long commandeId = toLong(commande.get("id"));
        for (CreerCommandeFournisseurRequest.LigneCommande ligne : request.getLignes()) {
            ingredientDao.findById(ligne.getIngredientId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Ingredient", ligne.getIngredientId()));
            commandeFournisseurDao.addLigne(
                    commandeId, ligne.getIngredientId(), ligne.getQuantite(), ligne.getPrixUnitaire());
        }
        return commandeFournisseurDao.findById(commandeId).orElseThrow();
    }
 
    public Map<String, Object> envoyer(Long id) {
        Map<String, Object> commande = findById(id);
        String statut = (String) commande.get("statut");
        if (!"EN_ATTENTE".equals(statut)) {
            throw new StatutInvalideException(statut, "EN_ATTENTE", "commande fournisseur #" + id);
        }
        commandeFournisseurDao.updateStatut(id, "ENVOYEE");
        return commandeFournisseurDao.findById(id).orElseThrow();
    }
 
    public Map<String, Object> recevoir(Long id) {
        Map<String, Object> commande = findById(id);
        String statut = (String) commande.get("statut");
        if (!"ENVOYEE".equals(statut)) {
            throw new StatutInvalideException(statut, "ENVOYEE", "commande fournisseur #" + id);
        }
        List<Map<String, Object>> lignes = commandeFournisseurDao.findLignes(id);
        for (Map<String, Object> ligne : lignes) {
            stockService.restituerStock(toLong(ligne.get("ingredientId")),
                    toDouble(ligne.get("quantite")));
        }
        commandeFournisseurDao.updateStatut(id, "RECUE");
        return commandeFournisseurDao.findById(id).orElseThrow();
    }
 
    public void supprimer(Long id) {
        findById(id);
        commandeFournisseurDao.delete(id);
    }
 
    private static Long toLong(Object o) {
        return o == null ? null : ((Number) o).longValue();
    }
 
    private static double toDouble(Object o) {
        return o == null ? 0.0 : ((Number) o).doubleValue();
    }
}