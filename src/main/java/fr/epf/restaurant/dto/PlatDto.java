package fr.epf.restaurant.dto;

import java.util.List;

import fr.epf.restaurant.model.Ingredient;

public class PlatDto {
    private long id;
    private String nom;
    private String description;
    private double prix;
    private List<Ingredient> ingredients;
    public PlatDto() {
    }
    public PlatDto(long id, String nom, String description, double prix, List<Ingredient> ingredients) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.ingredients = ingredients;
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
    public List<Ingredient> getIngredients() {
        return ingredients;
    }
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    
    


}
