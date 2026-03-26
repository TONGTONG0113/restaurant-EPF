package fr.epf.restaurant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.epf.restaurant.dao.ClientDao;
import fr.epf.restaurant.model.Client;
@Service
public class ClientService {
    private final ClientDao clientDao;

    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public void saveClient(Client client) {
        clientDao.save(client);
    }

    public List<Client> findAllClients() {
        return clientDao.findAll();
    }

}
