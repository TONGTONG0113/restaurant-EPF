package fr.epf.restaurant.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.epf.restaurant.dto.PlatDto;
import fr.epf.restaurant.model.Plat;
import fr.epf.restaurant.service.PlatService;

@RestController
@RequestMapping("/api/plats")
public class PlatController {
    private final PlatService platService;
    public PlatController(PlatService platService){
        this.platService=platService;

    }
    @GetMapping
    public List<Plat> getAllPlats(){
        return platService.getAllPlats();
    }

    @GetMapping("/{id}")
    public PlatDto getByIdPlat(@PathVariable long id){
        return platService.getById(id);
    }

    @PostMapping
    public void creatPlat(@RequestBody Plat plat){
        platService.creatPlat(plat);
    }

}
