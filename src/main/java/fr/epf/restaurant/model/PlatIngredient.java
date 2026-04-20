package fr.epf.restaurant.model;

public class PlatIngredient {
    private Long platId;
    private Long ingrediantId;
    private double quantiteRequise;
    public PlatIngredient() {
    }
    public PlatIngredient(Long platId, Long ingrediantId, double quantiteRequise) {
        this.platId = platId;
        this.ingrediantId = ingrediantId;
        this.quantiteRequise = quantiteRequise;
    }
    public Long getPlatId() {
        return platId;
    }
    public void setPlatId(Long platId) {
        this.platId = platId;
    }
    public Long getIngrediantId() {
        return ingrediantId;
    }
    public void setIngrediantId(Long ingrediantId) {
        this.ingrediantId = ingrediantId;
    }
    public double getQuantiteRequise() {
        return quantiteRequise;
    }
    public void setQuantiteRequise(double quantiteRequise) {
        this.quantiteRequise = quantiteRequise;
    }
    
        

}
