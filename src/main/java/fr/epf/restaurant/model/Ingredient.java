package fr.epf.restaurant.model;

public class Ingredient {
    private Long id;
    private String nom;
    private String unite;
    private double stockActuel;
    private double seuilAlerte;

    
    public Ingredient() {
    }

    
    public Ingredient(Long id, String nom, String unite, double stockActuel, double seuilAlerte) {
        this.id = id;
        this.nom = nom;
        this.unite = unite;
        this.stockActuel = stockActuel;
        this.seuilAlerte = seuilAlerte;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getUnite() {
        return unite;
    }
    public void setUnite(String unite) {
        this.unite = unite;
    }
    public double getStockActuel() {
        return stockActuel;
    }
    public void setStockActuel(double stockActuel) {
        this.stockActuel = stockActuel;
    }
    public double getSeuilAlerte() {
        return seuilAlerte;
    }
    public void setSeuilAlerte(double seuilAlerte) {
        this.seuilAlerte = seuilAlerte;
    }
    
    

}
