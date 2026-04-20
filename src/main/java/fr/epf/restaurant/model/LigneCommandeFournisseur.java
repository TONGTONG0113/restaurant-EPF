package fr.epf.restaurant.model;

public class LigneCommandeFournisseur {
    private Long id;
    private Long CommandeFournisseurId;
    private Long ingrediantId;
    private double quantiteCommandee;
    private double prixUnitaire;
    public LigneCommandeFournisseur() {
    }
    public LigneCommandeFournisseur(Long id, Long commandeFournisseurId, Long ingrediantId, double quantiteCommandee,
            double prixUnitaire) {
        this.id = id;
        CommandeFournisseurId = commandeFournisseurId;
        this.ingrediantId = ingrediantId;
        this.quantiteCommandee = quantiteCommandee;
        this.prixUnitaire = prixUnitaire;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getCommandeFournisseurId() {
        return CommandeFournisseurId;
    }
    public void setCommandeFournisseurId(Long commandeFournisseurId) {
        CommandeFournisseurId = commandeFournisseurId;
    }
    public Long getIngrediantId() {
        return ingrediantId;
    }
    public void setIngrediantId(Long ingrediantId) {
        this.ingrediantId = ingrediantId;
    }
    public double getQuantiteCommandee() {
        return quantiteCommandee;
    }
    public void setQuantiteCommandee(double quantiteCommandee) {
        this.quantiteCommandee = quantiteCommandee;
    }
    public double getPrixUnitaire() {
        return prixUnitaire;
    }
    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
    

    

}
