package fr.epf.restaurant.dto;

public class RecommandationsDto {
    private Long ingredientId;
    private String ingredientNom;
    private String unite;
    private int stockActuel;
    private int seuilAlerte;
    private Long fournisseurId;
    private String fournisseurNom;
    private double prixUnitaire;
    private int quantiteRecommandee;

    public RecommandationsDto() {
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientNom() {
        return ingredientNom;
    }

    public void setIngredientNom(String ingredientNom) {
        this.ingredientNom = ingredientNom;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public int getStockActuel() {
        return stockActuel;
    }

    public void setStockActuel(int stockActuel) {
        this.stockActuel = stockActuel;
    }

    public int getSeuilAlerte() {
        return seuilAlerte;
    }

    public void setSeuilAlerte(int seuilAlerte) {
        this.seuilAlerte = seuilAlerte;
    }

    public Long getFournisseurId() {
        return fournisseurId;
    }

    public void setFournisseurId(Long fournisseurId) {
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

    public int getQuantiteRecommandee() {
        return quantiteRecommandee;
    }

    public void setQuantiteRecommandee(int quantiteRecommandee) {
        this.quantiteRecommandee = quantiteRecommandee;
    }






}
