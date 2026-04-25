package fr.epf.restaurant.dto;

public class AlerteStockDto {
    private Long ingredientId;
    private String ingredientNom;
    private String unite;
    private int stockActuel;
    private int seuilAlerte;
    private int quantiteACommander;
 
    public AlerteStockDto() {
    }
 
    public AlerteStockDto(Long ingredientId, String ingredientNom, String unite,
            int stockActuel, int seuilAlerte, int quantiteACommander) {
        this.ingredientId = ingredientId;
        this.ingredientNom = ingredientNom;
        this.unite = unite;
        this.stockActuel = stockActuel;
        this.seuilAlerte = seuilAlerte;
        this.quantiteACommander = quantiteACommander;
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
 
    public int getQuantiteACommander() {
        return quantiteACommander;
    }
 
    public void setQuantiteACommander(int quantiteACommander) {
        this.quantiteACommander = quantiteACommander;
    }

}
