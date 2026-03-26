package fr.epf.restaurant.service;

import fr.epf.restaurant.dao.CommandeClientDao;
import fr.epf.restaurant.dao.IngredientDao;
import fr.epf.restaurant.dao.PlatDao;
import fr.epf.restaurant.dto.CreeRequestDto;
import fr.epf.restaurant.dto.LigneCommandeDto;
import fr.epf.restaurant.model.CommandeClient;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CommandeClientService {

    private final CommandeClientDao dao;
    private final PlatDao platDao;
    private final IngredientDao ingredientDao;

    public CommandeClientService(
            CommandeClientDao dao,
            PlatDao platDao,
            IngredientDao ingredientDao){

        this.dao = dao;
        this.platDao = platDao;
        this.ingredientDao = ingredientDao;
    }

    // CREATE COMMANDE
    public void createCommande(CreeRequestDto request){

        CommandeClient commande = new CommandeClient();

        commande.setClientId(request.getClientId());
        commande.setDateCommande(LocalDate.now());
        commande.setStatut("EN_ATTENTE");

        long commandeId = dao.saveCommande(commande);

        if(request.getLignes() != null){

            for(LigneCommandeDto ligne : request.getLignes()){
                dao.saveLigne(commandeId, ligne);
            }

        }
    }

    // GET ALL
    public List<CommandeClient> findAll(){
        return dao.findAll();
    }

    // GET BY ID
    public CommandeClient findById(long id){
        return dao.findById(id);
    }

    // PREPARER
    public void preparerCommande(long id){

        CommandeClient commande = dao.findById(id);

        if(commande == null){
            throw new RuntimeException("Commande inexistante");
        }

        if(!commande.getStatut().equals("EN_ATTENTE")){
            throw new RuntimeException("Statut incorrect");
        }

        dao.updateStatut(id,"EN_PREPARATION");
    }

    // SERVIR
    public void servirCommande(long id){

        CommandeClient commande = dao.findById(id);

        if(commande == null){
            throw new RuntimeException("Commande inexistante");
        }

        if(!commande.getStatut().equals("EN_PREPARATION")){
            throw new RuntimeException("Commande non prête");
        }

        dao.updateStatut(id,"SERVIE");
    }

    // DELETE
    public void deleteCommande(long id){
        dao.delete(id);
    }

}