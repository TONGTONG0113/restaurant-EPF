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
import org.springframework.web.bind.annotation.RestController;

import fr.epf.restaurant.dto.CreerCommandeFournisseurRequest;

import fr.epf.restaurant.service.CommandeFournisseurService;
@RestController
@RequestMapping("/api/commandes/fournisseur")
public class CommandeFournisseurController {
    private final CommandeFournisseurService service;
 
    public CommandeFournisseurController(CommandeFournisseurService service) {
        this.service = service;
    }
 
    @GetMapping
    public List<Map<String, Object>> getAll() {
        return service.findAll();
    }
 
    @GetMapping("/{id}")
    public Map<String, Object> getById(@PathVariable Long id) {
        return service.findById(id);
    }
 
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> body) {
        CreerCommandeFournisseurRequest request = new CreerCommandeFournisseurRequest();
 
        // Extraire fournisseurId — peut venir de "fournisseurId" ou de "fournisseur.id"
        if (body.containsKey("fournisseurId")) {
            request.setFournisseurId(((Number) body.get("fournisseurId")).longValue());
        } else if (body.containsKey("fournisseur")) {
            @SuppressWarnings("unchecked")
            Map<String, Object> f = (Map<String, Object>) body.get("fournisseur");
            request.setFournisseurId(((Number) f.get("id")).longValue());
        }
 
        // Extraire les lignes
        if (body.containsKey("lignes")) {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> lignesRaw = (List<Map<String, Object>>) body.get("lignes");
            List<CreerCommandeFournisseurRequest.LigneCommande> lignes = lignesRaw.stream().map(l -> {
                CreerCommandeFournisseurRequest.LigneCommande ligne = new CreerCommandeFournisseurRequest.LigneCommande();
                // ingredientId peut venir de "ingredientId" ou de "ingredient.id"
                if (l.containsKey("ingredientId")) {
                    ligne.setIngredientId(((Number) l.get("ingredientId")).longValue());
                } else if (l.containsKey("ingredient")) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> ing = (Map<String, Object>) l.get("ingredient");
                    ligne.setIngredientId(((Number) ing.get("id")).longValue());
                }
                ligne.setQuantite(((Number) l.get("quantite")).doubleValue());
                ligne.setPrixUnitaire(l.containsKey("prixUnitaire")
                        ? ((Number) l.get("prixUnitaire")).doubleValue() : 0.0);
                return ligne;
            }).toList();
            request.setLignes(lignes);
        } else {
            request.setLignes(List.of());
        }
 
        return ResponseEntity.status(201).body(service.creer(request));
    }
 
    @PutMapping("/{id}/envoyer")
    public ResponseEntity<Map<String, Object>> envoyer(@PathVariable Long id) {
        return ResponseEntity.ok(service.envoyer(id));
    }
 
    @PutMapping("/{id}/recevoir")
    public ResponseEntity<Map<String, Object>> recevoir(@PathVariable Long id) {
        return ResponseEntity.ok(service.recevoir(id));
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}
