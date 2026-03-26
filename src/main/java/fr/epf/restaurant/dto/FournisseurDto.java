package fr.epf.restaurant.dto;

public class FournisseurDto {
    private long id;
    private String nom;
    private double prixUnitaire;
    private String ingredientNom;
    private long ingredientId;
    private String ingredientUnite;

    public FournisseurDto() {
    }
    
    public FournisseurDto(long id, String nom, double prixUnitaire, String ingredientNom, long ingredientId,
            String ingredientUnite) {
        this.id = id;
        this.nom = nom;
        this.prixUnitaire = prixUnitaire;
        this.ingredientNom = ingredientNom;
        this.ingredientId = ingredientId;
        this.ingredientUnite = ingredientUnite;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public double getPrixUnitaire() {
        return prixUnitaire;
    }
    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public String getIngredientNom() {
        return ingredientNom;
    }

    public void setIngredientNom(String ingredientNom) {
        this.ingredientNom = ingredientNom;
    }

    public long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientUnite() {
        return ingredientUnite;
    }

    public void setIngredientUnite(String ingredientUnite) {
        this.ingredientUnite = ingredientUnite;
    }

    

    

}
