package fr.epf.restaurant.exception;

public class StockInsuffisantException extends RuntimeException{

    private final String ingredientNom;
    private final int stockDisponible;
    private final int stockRequis;
 
    public StockInsuffisantException(String ingredientNom, int stockDisponible, int stockRequis) {
        super("Stock insuffisant pour l'ingrédient '" + ingredientNom
                + "' : disponible=" + stockDisponible + ", requis=" + stockRequis);
        this.ingredientNom = ingredientNom;
        this.stockDisponible = stockDisponible;
        this.stockRequis = stockRequis;
    }
 
    public StockInsuffisantException(String message) {
        super(message);
        this.ingredientNom = null;
        this.stockDisponible = 0;
        this.stockRequis = 0;
    }
 
    public String getIngredientNom() { return ingredientNom; }
    public int getStockDisponible() { return stockDisponible; }
    public int getStockRequis() { return stockRequis; }

    
    

}
