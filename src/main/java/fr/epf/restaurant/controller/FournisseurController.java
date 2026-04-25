package fr.epf.restaurant.controller;

import fr.epf.restaurant.dao.FournisseurDao;
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
@RequestMapping("/api/fournisseurs")
public class FournisseurController {

    private final FournisseurDao fournisseurDao;

    public FournisseurController(FournisseurDao fournisseurDao) {
        this.fournisseurDao = fournisseurDao;
    }

    @GetMapping
    public List<Map<String, Object>> getAll() {
        return fournisseurDao.findAll();
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> body) {
        String nom = (String) body.get("nom");
        String contact = (String) body.getOrDefault("contact", "");
        String email = (String) body.getOrDefault("email", "");
        return ResponseEntity.status(201).body(fournisseurDao.create(nom, contact, email));
    }

    @GetMapping("/{id}/catalogue")
    public List<Map<String, Object>> getCatalogue(@PathVariable Long id) {
        fournisseurDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur", id));
        return fournisseurDao.findCatalogue(id);
    }
}

