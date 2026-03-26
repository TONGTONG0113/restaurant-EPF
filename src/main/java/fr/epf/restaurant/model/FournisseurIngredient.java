package fr.epf.restaurant.model;

public class FournisseurIngredient {
    private long fournisseurId;
    private long ingrediantId;
    private double prixUnitaire;
    public FournisseurIngredient() {
    }
    public FournisseurIngredient(long fournisseurId, long ingrediantId, double prixUnitaire) {
        this.fournisseurId = fournisseurId;
        this.ingrediantId = ingrediantId;
        this.prixUnitaire = prixUnitaire;
    }
    public long getFournisseurId() {
        return fournisseurId;
    }
    public void setFournisseurId(long fournisseurId) {
        this.fournisseurId = fournisseurId;
    }
    public long getIngrediantId() {
        return ingrediantId;
    }
    public void setIngrediantId(long ingrediantId) {
        this.ingrediantId = ingrediantId;
    }
    public double getPrixUnitaire() {
        return prixUnitaire;
    }
    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    

}
