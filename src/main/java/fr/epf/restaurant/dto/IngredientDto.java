package fr.epf.restaurant.dto;



public class IngredientDto {
    private long fournisseurId;
    private String fournisseurNom;
    private double prixUnitaire;
    public IngredientDto() {
    }
    
    public IngredientDto(long fournisseurId, String fournisseurNom, double prixUnitaire) {
        this.fournisseurId = fournisseurId;
        this.fournisseurNom = fournisseurNom;
        this.prixUnitaire = prixUnitaire;
    }
    public long getFournisseurId() {
        return fournisseurId;
    }
    public void setFournisseurId(long fournisseurId) {
        this.fournisseurId = fournisseurId;
    }
    public String getFournisseurNom() {
        return fournisseurNom;
    }
    public void setFournisseurNom(String fournisseurNom) {
        this.fournisseurNom = fournisseurNom;
    }
    public double getPrixUnitaire() {
        return prixUnitaire;
    }
    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
    
    
    
    

}
