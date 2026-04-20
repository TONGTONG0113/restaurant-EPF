package fr.epf.restaurant.dto;

import java.util.List;

public class CreerCommandeClientRequest {
    private Long clientId;
    private List<LigneCommande> lignes;
 
    public static class LigneCommande {
        private Long platId;
        private int quantite;
 
        public Long getPlatId() { return platId; }
        public void setPlatId(Long platId) { this.platId = platId; }
        public int getQuantite() { return quantite; }
        public void setQuantite(int quantite) { this.quantite = quantite; }
    }
 
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public List<LigneCommande> getLignes() { return lignes; }
    public void setLignes(List<LigneCommande> lignes) { this.lignes = lignes; }
    

}
