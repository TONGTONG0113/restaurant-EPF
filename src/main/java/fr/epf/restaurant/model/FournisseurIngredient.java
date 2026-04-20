package fr.epf.restaurant.model;

public class FournisseurIngredient {
    private Long fournisseurId;
    private Long ingrediantId;
    private double prixUnitaire;
    public FournisseurIngredient() {
    }

    
    public FournisseurIngredient(Long fournisseurId, Long ingrediantId, double prixUnitaire) {
        this.fournisseurId = fournisseurId;
        this.ingrediantId = ingrediantId;
        this.prixUnitaire = prixUnitaire;
    }


    public Long getFournisseurId() {
        return fournisseurId;
    }
    public void setFournisseurId(Long fournisseurId) {
        this.fournisseurId = fournisseurId;
    }
    public Long getIngrediantId() {
        return ingrediantId;
    }
    public void setIngrediantId(Long ingrediantId) {
        this.ingrediantId = ingrediantId;
    }
    public double getPrixUnitaire() {
        return prixUnitaire;
    }
    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
    

    

}
