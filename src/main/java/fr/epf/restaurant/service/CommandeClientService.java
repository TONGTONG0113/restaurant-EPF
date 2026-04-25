package fr.epf.restaurant.service;

import fr.epf.restaurant.dao.ClientDao;
import fr.epf.restaurant.dao.CommandeClientDao;
import fr.epf.restaurant.dao.IngredientDao;
import fr.epf.restaurant.dao.PlatDao;
import fr.epf.restaurant.dto.AlerteStockDto;
import fr.epf.restaurant.dto.CreerCommandeClientRequest;
import fr.epf.restaurant.dto.PreparationResultDto;
import fr.epf.restaurant.exception.ResourceNotFoundException;
import fr.epf.restaurant.exception.StatutInvalideException;
import fr.epf.restaurant.exception.StockInsuffisantException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommandeClientService {

    private final CommandeClientDao commandeClientDao;
    private final ClientDao clientDao;
    private final PlatDao platDao;
    private final IngredientDao ingredientDao;
    private final StockService stockService;

    public CommandeClientService(CommandeClientDao commandeClientDao,
            ClientDao clientDao, PlatDao platDao,
            IngredientDao ingredientDao, StockService stockService) {
        this.commandeClientDao = commandeClientDao;
        this.clientDao = clientDao;
        this.platDao = platDao;
        this.ingredientDao = ingredientDao;
        this.stockService = stockService;
    }

    public List<Map<String, Object>> findAll(String statut) {
        return commandeClientDao.findAll(statut);
    }

    public Map<String, Object> findById(Long id) {
        return commandeClientDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande client", id));
    }

    public Map<String, Object> creer(CreerCommandeClientRequest request) {
        if (request.getClientId() == null) {
            throw new IllegalArgumentException("clientId est obligatoire");
        }
        clientDao.findById(request.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client", request.getClientId()));
        Map<String, Object> commande = commandeClientDao.create(request.getClientId());
        Long commandeId = toLong(commande.get("id"));
        if (request.getLignes() != null) {
            for (CreerCommandeClientRequest.LigneCommande ligne : request.getLignes()) {
                if (ligne.getPlatId() == null) {
                    continue;
                }
                platDao.findById(ligne.getPlatId())
                        .orElseThrow(() -> new ResourceNotFoundException("Plat", ligne.getPlatId()));
                commandeClientDao.addLigne(commandeId, ligne.getPlatId(), ligne.getQuantite());
            }
        }
        return commandeClientDao.findById(commandeId).orElseThrow();
    }

    public PreparationResultDto passerEnPreparation(Long id) {
        Map<String, Object> commande = findById(id);
        String statut = (String) commande.get("statut");
        if (!"EN_ATTENTE".equals(statut)) {
            throw new StatutInvalideException(statut, "EN_ATTENTE", "commande client #" + id);
        }
        List<Map<String, Object>> besoins = commandeClientDao.findBesoinsIngredients(id);
        Map<Long, Double> total = new HashMap<>();
        for (Map<String, Object> besoin : besoins) {
            Long ingId = toLong(besoin.get("ingredientId"));
            int qPlat = toInt(besoin.get("quantitePlat"));
            double qReq = toDouble(besoin.get("quantiteRequise"));
            total.merge(ingId, qPlat * qReq, Double::sum);
        }
        for (Map.Entry<Long, Double> entry : total.entrySet()) {
            Map<String, Object> ing = ingredientDao.findById(entry.getKey())
                    .orElseThrow(() -> new ResourceNotFoundException("Ingredient", entry.getKey()));
            double stock = toDouble(ing.get("stockActuel"));
            if (stock < entry.getValue()) {
                throw new StockInsuffisantException(
                        (String) ing.get("nom"), (int) stock, entry.getValue().intValue());
            }
        }
        for (Map.Entry<Long, Double> entry : total.entrySet()) {
            stockService.consommerStock(entry.getKey(), entry.getValue());
        }
        commandeClientDao.updateStatut(id, "EN_PREPARATION");
        Map<String, Object> maj = commandeClientDao.findById(id).orElseThrow();
        List<AlerteStockDto> alertes = stockService.getAlertes();
        return new PreparationResultDto(maj, alertes);
    }

    public Map<String, Object> marquerServie(Long id) {
        Map<String, Object> commande = findById(id);
        String statut = (String) commande.get("statut");
        if (!"EN_PREPARATION".equals(statut)) {
            throw new StatutInvalideException(statut, "EN_PREPARATION", "commande client #" + id);
        }
        commandeClientDao.updateStatut(id, "SERVIE");
        return commandeClientDao.findById(id).orElseThrow();
    }

    public void supprimer(Long id) {
        findById(id);
        commandeClientDao.delete(id);
    }

    private static Long toLong(Object o) {
        return o == null ? null : ((Number) o).longValue();
    }

    private static int toInt(Object o) {
        return o == null ? 0 : ((Number) o).intValue();
    }

    private static double toDouble(Object o) {
        return o == null ? 0.0 : ((Number) o).doubleValue();
    }
}