package fr.epf.restaurant.exception;

public class StockInsuffisantException extends RuntimeException {
 
    private final String ingredientNom;
    private final int stockDisponible;
    private final int stockRequis;
 
    public StockInsuffisantException(String ingredientNom, int stockDisponible, int stockRequis) {
        super("Stock insuffisant pour '" + ingredientNom
                + "' : disponible=" + stockDisponible + ", requis=" + stockRequis);
        this.ingredientNom = ingredientNom;
        this.stockDisponible = stockDisponible;
        this.stockRequis = stockRequis;
    }
 
    public String getIngredientNom() {
        return ingredientNom;
    }
 
    public int getStockDisponible() {
        return stockDisponible;
    }
 
    public int getStockRequis() {
        return stockRequis;
    }
}
