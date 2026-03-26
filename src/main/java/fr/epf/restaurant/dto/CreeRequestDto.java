package fr.epf.restaurant.dto;

import java.util.List;

public class CreeRequestDto {
    private long ClientId;
    private List<LigneCommandeDto> lignes;
    public CreeRequestDto(long clientId, List<LigneCommandeDto> lignes) {
        ClientId = clientId;
        this.lignes = lignes;
    }
    public long getClientId() {
        return ClientId;
    }
    public void setClientId(long clientId) {
        ClientId = clientId;
    }
    public List<LigneCommandeDto> getLignes() {
        return lignes;
    }
    public void setLignes(List<LigneCommandeDto> lignes) {
        this.lignes = lignes;
    }
    

}
