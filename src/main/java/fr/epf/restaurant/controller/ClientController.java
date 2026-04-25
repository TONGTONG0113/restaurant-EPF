package fr.epf.restaurant.controller;

import fr.epf.restaurant.dao.ClientDao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientDao clientDao;

    public ClientController(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @GetMapping
    public List<Map<String, Object>> getAll() {
        return clientDao.findAll();
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> body) {
        String nom = (String) body.get("nom");
        String prenom = (String) body.get("prenom");
        String email = (String) body.getOrDefault("email", "");
        String telephone = (String) body.getOrDefault("telephone", "");
        return ResponseEntity.status(201).body(clientDao.create(nom, prenom, email, telephone));
    }

}
