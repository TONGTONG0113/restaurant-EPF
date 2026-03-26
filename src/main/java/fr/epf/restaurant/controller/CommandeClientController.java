package fr.epf.restaurant.controller;

import fr.epf.restaurant.dto.CreeRequestDto;
import fr.epf.restaurant.model.CommandeClient;
import fr.epf.restaurant.service.CommandeClientService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commandes/client")
public class CommandeClientController {

    private final CommandeClientService service;

    public CommandeClientController(CommandeClientService service){
        this.service = service;
    }

    @GetMapping
    public List<CommandeClient> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public CommandeClient findById(@PathVariable long id){
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreeRequestDto request){
        service.createCommande(request);
    }

    @PutMapping("/{id}/preparer")
    public void preparer(@PathVariable long id){
        service.preparerCommande(id);
    }

    @PutMapping("/{id}/servir")
    public void servir(@PathVariable long id){
        service.servirCommande(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id){
        service.deleteCommande(id);
    }
}