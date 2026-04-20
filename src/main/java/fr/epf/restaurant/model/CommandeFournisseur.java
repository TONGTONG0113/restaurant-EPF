package fr.epf.restaurant.model;

import java.time.LocalDate;

public class CommandeFournisseur {
    private Long id;
    private Long fournisseurId;
    private LocalDate dateCommande;
    private String statut;
    public CommandeFournisseur() {
    }

    public CommandeFournisseur(Long id, Long fournisseurId, LocalDate dateCommande, String statut) {
        this.id = id;
        this.fournisseurId = fournisseurId;
        this.dateCommande = dateCommande;
        this.statut = statut;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getFournisseurId() {
        return fournisseurId;
    }
    public void setFournisseurId(Long fournisseurId) {
        this.fournisseurId = fournisseurId;
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
