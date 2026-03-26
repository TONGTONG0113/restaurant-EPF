package fr.epf.restaurant.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.epf.restaurant.model.Client;
import fr.epf.restaurant.service.ClientService;
@RestController
@RequestMapping("/api/clients")
public class ClientController {
    public final ClientService clientservice;
    public ClientController(ClientService clientservice) {
        this.clientservice = clientservice;
    }
    @GetMapping
    public List<Client> getAllClients() {
        return clientservice.findAllClients();
    }

    @PostMapping
    public void saveClient(@RequestBody Client client) {
        clientservice.saveClient(client);
    }

}
