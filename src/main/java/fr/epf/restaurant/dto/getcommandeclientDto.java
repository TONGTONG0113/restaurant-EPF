package fr.epf.restaurant.dto;

import java.time.LocalDate;
import java.util.List;

public class getcommandeclientDto {
    private long id;
    private long clientId;
    private LocalDate dateCommande;
    private String statut;
    private List<LigneCommandeDto> lignes;

    
    public getcommandeclientDto(long id, long clientId, LocalDate dateCommande, String statut,
            List<LigneCommandeDto> lignes) {
        this.id = id;
        this.clientId = clientId;
        this.dateCommande = dateCommande;
        this.statut = statut;
        this.lignes = lignes;
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
    public List<LigneCommandeDto> getLignes() {
        return lignes;
    }
    public void setLignes(List<LigneCommandeDto> lignes) {
        this.lignes = lignes;
    }
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isEmpty'");
    }

    

}
