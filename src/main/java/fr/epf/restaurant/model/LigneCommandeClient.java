package fr.epf.restaurant.model;

public class LigneCommandeClient {
    private Long id;
    private Long commandeClientId;
    private Long platId;
    private int quantite;


    
    public LigneCommandeClient() {
    }

    
    public LigneCommandeClient(Long id, Long commandeClientId, Long platId, int quantite) {
        this.id = id;
        this.commandeClientId = commandeClientId;
        this.platId = platId;
        this.quantite = quantite;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getCommandeClientId() {
        return commandeClientId;
    }
    public void setCommandeClientId(Long commandeClientId) {
        this.commandeClientId = commandeClientId;
    }
    public Long getPlatId() {
        return platId;
    }
    public void setPlatId(Long platId) {
        this.platId = platId;
    }
    public int getQuantite() {
        return quantite;
    }
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    
    
    

}
