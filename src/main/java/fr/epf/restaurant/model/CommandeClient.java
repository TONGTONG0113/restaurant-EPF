package fr.epf.restaurant.model;

import java.time.LocalDate;

public class CommandeClient {
    private long id;
    private long clientId;
    private LocalDate dateCommande;
    private String statut;

    public CommandeClient() {
    }
    
    public CommandeClient(long id, long clientId, LocalDate dateCommande, String statut) {
        this.id = id;
        this.clientId = clientId;
        this.dateCommande = dateCommande;
        this.statut = statut;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getClientId() {
        return clientId;
    }
    public void setClientId(long clientId) {
        this.clientId = clientId;
    }
    public LocalDate getDateCommande() {
        return dateCommande;
    }
    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }
    public String getStatut() {
        return statut;
    }
    public void setStatut(String statut) {
        this.statut = statut;
    }
    

}
