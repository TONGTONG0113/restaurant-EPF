package fr.epf.restaurant.model;

public class LigneCommandeClient {
    private long id;
    private long commandeClientId;
    private long platId;
    private int quantite;
    public LigneCommandeClient() {
    }
    public LigneCommandeClient(long id, long commandeClientId, long platId, int quantite) {
        this.id = id;
        this.commandeClientId = commandeClientId;
        this.platId = platId;
        this.quantite = quantite;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getCommandeClientId() {
        return commandeClientId;
    }
    public void setCommandeClientId(long commandeClientId) {
        this.commandeClientId = commandeClientId;
    }
    public long getPlatId() {
        return platId;
    }
    public void setPlatId(long platId) {
        this.platId = platId;
    }
    public int getQuantite() {
        return quantite;
    }
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    

}
