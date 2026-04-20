package fr.epf.restaurant.model;

import java.time.LocalDate;

public class CommandeClient {
    private Long id;
    private Long clientId;
    private LocalDate dateCommande;
    private String statut;

    

    public CommandeClient(Long id, Long clientId, LocalDate dateCommande, String statut) {
        this.id = id;
        this.clientId = clientId;
        this.dateCommande = dateCommande;
        this.statut = statut;
    }

    public CommandeClient() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
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
