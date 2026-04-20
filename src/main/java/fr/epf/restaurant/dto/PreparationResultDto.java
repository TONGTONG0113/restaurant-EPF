package fr.epf.restaurant.dto;

import java.util.List;
import java.util.Map;



public class PreparationResultDto {
     private Map<String, Object> commande;
    private List<AlerteStockDto> alertes;
 
    public PreparationResultDto() {}
 
    public PreparationResultDto(Map<String, Object> commande, List<AlerteStockDto> alertes) {
        this.commande = commande;
        this.alertes = alertes;
    }
 
    public Map<String, Object> getCommande() { return commande; }
    public void setCommande(Map<String, Object> commande) { this.commande = commande; }
 
    public List<AlerteStockDto> getAlertes() { return alertes; }
    public void setAlertes(List<AlerteStockDto> alertes) { this.alertes = alertes; }
}

    



