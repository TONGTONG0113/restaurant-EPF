package fr.epf.restaurant.dto;

public class RecommandationsDto {
    private String fournisseurNom;
    private long fournisseurId;
    private int quantiterecommandee;
    private double prixUnitaire;
    
    public RecommandationsDto() {
    }
    public RecommandationsDto(String fournisseurNom, long fournisseurId, int quantiterecommandee, double prixUnitaire) {
        this.fournisseurNom = fournisseurNom;
        this.fournisseurId = fournisseurId;
        this.quantiterecommandee = quantiterecommandee;
        this.prixUnitaire = prixUnitaire;
    }
    
    public String getFournisseurNom() {
        return fournisseurNom;
    }
    public void setFournisseurNom(String fournisseurNom) {
        this.fournisseurNom = fournisseurNom;
    }
    public long getFournisseurId() {
        return fournisseurId;
    }
    public void setFournisseurId(long fournisseurId) {
        this.fournisseurId = fournisseurId;
    }
    public int getQuantiterecommandee() {
        return quantiterecommandee;
    }
    public void setQuantiterecommandee(int quantiterecommandee) {
        this.quantiterecommandee = quantiterecommandee;
    }
    public double getPrixUnitaire() {
        return prixUnitaire;
    }
    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    

}
