package fr.epf.restaurant.controller;

import fr.epf.restaurant.dto.CreerCommandeFournisseurRequest;
import fr.epf.restaurant.service.CommandeFournisseurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        if (body.containsKey("fournisseurId") && body.get("fournisseurId") != null) {
            request.setFournisseurId(((Number) body.get("fournisseurId")).longValue());
        } else if (body.containsKey("fournisseur") && body.get("fournisseur") != null) {
            Map<String, Object> f = (Map<String, Object>) body.get("fournisseur");
            request.setFournisseurId(((Number) f.get("id")).longValue());
        }
        List<CreerCommandeFournisseurRequest.LigneCommande> lignes = new ArrayList<>();
        if (body.containsKey("lignes") && body.get("lignes") != null) {
            List<Map<String, Object>> lignesRaw = (List<Map<String, Object>>) body.get("lignes");
            for (Map<String, Object> l : lignesRaw) {
                CreerCommandeFournisseurRequest.LigneCommande ligne =
                        new CreerCommandeFournisseurRequest.LigneCommande();
                if (l.containsKey("ingredientId") && l.get("ingredientId") != null) {
                    ligne.setIngredientId(((Number) l.get("ingredientId")).longValue());
                } else if (l.containsKey("ingredient") && l.get("ingredient") != null) {
                    Map<String, Object> ing = (Map<String, Object>) l.get("ingredient");
                    ligne.setIngredientId(((Number) ing.get("id")).longValue());
                }
                ligne.setQuantite(((Number) l.get("quantite")).doubleValue());
                if (l.containsKey("prixUnitaire") && l.get("prixUnitaire") != null) {
                    ligne.setPrixUnitaire(((Number) l.get("prixUnitaire")).doubleValue());
                }
                lignes.add(ligne);
            }
        }
        request.setLignes(lignes);
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
