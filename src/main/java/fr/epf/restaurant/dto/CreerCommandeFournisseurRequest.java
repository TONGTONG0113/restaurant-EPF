package fr.epf.restaurant.dto;

import java.util.List;

public class CreerCommandeFournisseurRequest {
    private Long fournisseurId;
    private List<LigneCommande> lignes;
 
    public static class LigneCommande {
        private Long ingredientId;
        private double quantite;       // DOUBLE dans le schéma (quantite_commandee)
        private double prixUnitaire;
 
        public Long getIngredientId() { return ingredientId; }
        public void setIngredientId(Long ingredientId) { this.ingredientId = ingredientId; }
        public double getQuantite() { return quantite; }
        public void setQuantite(double quantite) { this.quantite = quantite; }
        public double getPrixUnitaire() { return prixUnitaire; }
        public void setPrixUnitaire(double prixUnitaire) { this.prixUnitaire = prixUnitaire; }
    }
 
    public Long getFournisseurId() { return fournisseurId; }
    public void setFournisseurId(Long fournisseurId) { this.fournisseurId = fournisseurId; }
    public List<LigneCommande> getLignes() { return lignes; }
    public void setLignes(List<LigneCommande> lignes) { this.lignes = lignes; }


}
