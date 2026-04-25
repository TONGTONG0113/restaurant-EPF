package fr.epf.restaurant.controller;

import fr.epf.restaurant.dto.CreerCommandeClientRequest;
import fr.epf.restaurant.dto.PreparationResultDto;
import fr.epf.restaurant.service.CommandeClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/commandes/client")
public class CommandeClientController {

    private final CommandeClientService service;

    public CommandeClientController(CommandeClientService service) {
        this.service = service;
    }

    @GetMapping
    public List<Map<String, Object>> getAll(@RequestParam(required = false) String statut) {
        return service.findAll(statut);
    }

    @GetMapping("/{id}")
    public Map<String, Object> getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> body) {
        CreerCommandeClientRequest request = new CreerCommandeClientRequest();
        if (body.containsKey("clientId") && body.get("clientId") != null) {
            request.setClientId(((Number) body.get("clientId")).longValue());
        } else if (body.containsKey("client") && body.get("client") != null) {
            Map<String, Object> client = (Map<String, Object>) body.get("client");
            request.setClientId(((Number) client.get("id")).longValue());
        }
        List<CreerCommandeClientRequest.LigneCommande> lignes = new ArrayList<>();
        if (body.containsKey("lignes") && body.get("lignes") != null) {
            List<Map<String, Object>> lignesRaw = (List<Map<String, Object>>) body.get("lignes");
            for (Map<String, Object> l : lignesRaw) {
                CreerCommandeClientRequest.LigneCommande ligne =
                        new CreerCommandeClientRequest.LigneCommande();
                if (l.containsKey("platId") && l.get("platId") != null) {
                    ligne.setPlatId(((Number) l.get("platId")).longValue());
                } else if (l.containsKey("plat") && l.get("plat") != null) {
                    Map<String, Object> plat = (Map<String, Object>) l.get("plat");
                    ligne.setPlatId(((Number) plat.get("id")).longValue());
                }
                ligne.setQuantite(((Number) l.get("quantite")).intValue());
                lignes.add(ligne);
            }
        }
        request.setLignes(lignes);
        return ResponseEntity.status(201).body(service.creer(request));
    }

    @PutMapping("/{id}/preparer")
    public ResponseEntity<PreparationResultDto> preparer(@PathVariable Long id) {
        return ResponseEntity.ok(service.passerEnPreparation(id));
    }

    @PutMapping("/{id}/servir")
    public ResponseEntity<Map<String, Object>> servir(@PathVariable Long id) {
        return ResponseEntity.ok(service.marquerServie(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}
