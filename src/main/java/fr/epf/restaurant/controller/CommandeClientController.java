package fr.epf.restaurant.controller;

import java.util.List;
import java.util.Map;

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

import fr.epf.restaurant.dto.CreerCommandeClientRequest;
import fr.epf.restaurant.dto.PreparationResultDto;
import fr.epf.restaurant.service.CommandeClientService;

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
        System.out.println("=== POST /api/commandes/client body: " + body);
 
        CreerCommandeClientRequest request = new CreerCommandeClientRequest();
 
        // clientId : supporte "clientId", "client_id", ou objet "client"
        if (body.containsKey("clientId") && body.get("clientId") != null) {
            request.setClientId(((Number) body.get("clientId")).longValue());
        } else if (body.containsKey("client_id") && body.get("client_id") != null) {
            request.setClientId(((Number) body.get("client_id")).longValue());
        } else if (body.containsKey("client") && body.get("client") != null) {
            @SuppressWarnings("unchecked")
            Map<String, Object> client = (Map<String, Object>) body.get("client");
            request.setClientId(((Number) client.get("id")).longValue());
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "clientId manquant dans le body: " + body));
        }
 
        // lignes
        List<CreerCommandeClientRequest.LigneCommande> lignes = List.of();
        if (body.containsKey("lignes") && body.get("lignes") != null) {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> lignesRaw = (List<Map<String, Object>>) body.get("lignes");
            lignes = lignesRaw.stream().map(l -> {
                System.out.println("  ligne: " + l);
                CreerCommandeClientRequest.LigneCommande ligne = new CreerCommandeClientRequest.LigneCommande();
                if (l.containsKey("platId") && l.get("platId") != null) {
                    ligne.setPlatId(((Number) l.get("platId")).longValue());
                } else if (l.containsKey("plat_id") && l.get("plat_id") != null) {
                    ligne.setPlatId(((Number) l.get("plat_id")).longValue());
                } else if (l.containsKey("plat") && l.get("plat") != null) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> plat = (Map<String, Object>) l.get("plat");
                    ligne.setPlatId(((Number) plat.get("id")).longValue());
                }
                ligne.setQuantite(((Number) l.get("quantite")).intValue());
                return ligne;
            }).toList();
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
