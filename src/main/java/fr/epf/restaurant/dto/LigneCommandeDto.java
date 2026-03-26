package fr.epf.restaurant.dto;

public class LigneCommandeDto {
    private long PlatId;
    private int quantite;
    public LigneCommandeDto() {
    }
    
    
    public LigneCommandeDto(long platId, int quantite) {
        PlatId = platId;
        this.quantite = quantite;
    }


    public long getPlatId() {
        return PlatId;
    }
    public void setPlatId(long platId) {
        PlatId= platId;
    }
    public int getQuantite() {
        return quantite;
    }
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    


}
