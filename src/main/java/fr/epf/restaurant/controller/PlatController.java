package fr.epf.restaurant.controller;

import fr.epf.restaurant.dao.PlatDao;
import fr.epf.restaurant.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/plats")
public class PlatController {

    private final PlatDao platDao;

    public PlatController(PlatDao platDao) {
        this.platDao = platDao;
    }

    @GetMapping
    public List<Map<String, Object>> getAll() {
        return platDao.findAll();
    }

    @GetMapping("/{id}")
    public Map<String, Object> getById(@PathVariable Long id) {
        return platDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plat", id));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> body) {
        String nom = (String) body.get("nom");
        String description = (String) body.getOrDefault("description", "");
        double prix = ((Number) body.get("prix")).doubleValue();
        Map<String, Object> plat = platDao.create(nom, description, prix);
        Long platId = ((Number) plat.get("id")).longValue();
        if (body.containsKey("ingredients")) {
            List<Map<String, Object>> ings = (List<Map<String, Object>>) body.get("ingredients");
            for (Map<String, Object> ing : ings) {
                Long ingredientId = ((Number) ing.get("ingredientId")).longValue();
                double quantite = ((Number) ing.get("quantite")).doubleValue();
                platDao.addIngredient(platId, ingredientId, quantite);
            }
            plat = platDao.findById(platId).orElseThrow();
        }
        return ResponseEntity.status(201).body(plat);
    }
}
