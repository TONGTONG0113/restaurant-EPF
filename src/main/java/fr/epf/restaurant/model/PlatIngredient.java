package fr.epf.restaurant.model;

public class PlatIngredient {
    private long platId;
    private long ingrediantId;
    private double quantiteRequise;
    public PlatIngredient() {
    }
    public PlatIngredient(long platId, long ingrediantId, double quantiteRequise) {
        this.platId = platId;
        this.ingrediantId = ingrediantId;
        this.quantiteRequise = quantiteRequise;
    }
    public long getPlatId() {
        return platId;
    }
    public void setPlatId(long platId) {
        this.platId = platId;
    }
    public long getIngrediantId() {
        return ingrediantId;
    }
    public void setIngrediantId(long ingrediantId) {
        this.ingrediantId = ingrediantId;
    }
    public double getQuantiteRequise() {
        return quantiteRequise;
    }
    public void setQuantiteRequise(double quantiteRequise) {
        this.quantiteRequise = quantiteRequise;
    }

        

}
