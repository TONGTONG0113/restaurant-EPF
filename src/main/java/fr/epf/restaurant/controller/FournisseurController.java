package fr.epf.restaurant.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.epf.restaurant.dto.FournisseurDto;
import fr.epf.restaurant.model.Fournisseur;
import fr.epf.restaurant.service.FournisseurService;
@RestController
@RequestMapping("/api/fournisseurs")
public class FournisseurController {
    private final FournisseurService fournisseurService;

    public FournisseurController(FournisseurService fournisseurService) {
        this.fournisseurService = fournisseurService;
    }
    @GetMapping
    public List<Fournisseur> getAll(){
       return fournisseurService.getAll();
    }
    @GetMapping("/{id}/catalogue")
    public List<FournisseurDto> getPrixById(@PathVariable long id){
        return fournisseurService.getPrixById(id);
    }

}

