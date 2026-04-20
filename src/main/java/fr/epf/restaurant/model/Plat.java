package fr.epf.restaurant.model;

public class Plat {
    private Long id;
    private String nom;
    private String description;
    private double prix;
   


    public Plat() {
    }



    public Plat(Long id, String nom, String description, double prix) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
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



    public String getDescription() {
        return description;
    }



    public void setDescription(String description) {
        this.description = description;
    }



    public double getPrix() {
        return prix;
    }



    public void setPrix(double prix) {
        this.prix = prix;
    }
    
    

    

}
