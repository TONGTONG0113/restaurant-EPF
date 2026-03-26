package fr.epf.restaurant.model;

public class LigneCommandeFournisseur {
    private long id;
    private long CommandeFournisseurId;
    private long ingrediantId;
    private double quantiteCommandee;
    private double prixUnitaire;
    public LigneCommandeFournisseur() {
    }
    public LigneCommandeFournisseur(long id, long commandeFournisseurId, long ingrediantId, double quantiteCommandee,
            double prixUnitaire) {
        this.id = id;
        CommandeFournisseurId = commandeFournisseurId;
        this.ingrediantId = ingrediantId;
        this.quantiteCommandee = quantiteCommandee;
        this.prixUnitaire = prixUnitaire;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getCommandeFournisseurId() {
        return CommandeFournisseurId;
    }
    public void setCommandeFournisseurId(long commandeFournisseurId) {
        CommandeFournisseurId = commandeFournisseurId;
    }
    public long getIngrediantId() {
        return ingrediantId;
    }
    public void setIngrediantId(long ingrediantId) {
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
