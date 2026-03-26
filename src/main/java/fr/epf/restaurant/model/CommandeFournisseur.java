package fr.epf.restaurant.model;

import java.time.LocalDate;

public class CommandeFournisseur {
    private long id;
    private long fournisseurId;
    private LocalDate dateCommande;
    private String statut;
    public CommandeFournisseur() {
    }
    public CommandeFournisseur(long id, long fournisseurId, LocalDate dateCommande, String statut) {
        this.id = id;
        this.fournisseurId = fournisseurId;
        this.dateCommande = dateCommande;
        this.statut = statut;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getFournisseurId() {
        return fournisseurId;
    }
    public void setFournisseurId(long fournisseurId) {
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
