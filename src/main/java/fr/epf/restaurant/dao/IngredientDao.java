package fr.epf.restaurant.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
 
@Repository
public class IngredientDao {
 
    private final JdbcTemplate jdbc;
 
    public IngredientDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
 
    public List<Map<String, Object>> findAll() {
        return jdbc.query(
                "SELECT id, nom, unite, stock_actuel, seuil_alerte FROM INGREDIENT ORDER BY id",
                (rs, i) -> toMap(rs));
    }
 
    public Optional<Map<String, Object>> findById(Long id) {
        List<Map<String, Object>> rows = jdbc.query(
                "SELECT id, nom, unite, stock_actuel, seuil_alerte FROM INGREDIENT WHERE id = ?",
                (rs, i) -> toMap(rs), id);
        return rows.isEmpty() ? Optional.empty() : Optional.of(rows.get(0));
    }
 
    /** stock_actuel < seuil_alerte (strictement inférieur) */
    public List<Map<String, Object>> findSousAlerte() {
        return jdbc.query(
                "SELECT id, nom, unite, stock_actuel, seuil_alerte FROM INGREDIENT WHERE stock_actuel < seuil_alerte ORDER BY id",
                (rs, i) -> toMap(rs));
    }
 
    public void updateStock(Long id, double newStock) {
        jdbc.update("UPDATE INGREDIENT SET stock_actuel = ? WHERE id = ?", newStock, id);
    }
 
    public Map<String, Object> create(String nom, String unite, double stockActuel, double seuilAlerte) {
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO INGREDIENT (nom, unite, stock_actuel, seuil_alerte) VALUES (?, ?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, nom);
            ps.setString(2, unite);
            ps.setDouble(3, stockActuel);
            ps.setDouble(4, seuilAlerte);
            return ps;
        }, kh);
        return findById(kh.getKey().longValue()).orElseThrow();
    }
 
    public List<Map<String, Object>> findPrixParFournisseur(Long ingredientId) {
        String sql = """
                SELECT fi.fournisseur_id AS fournisseurId,
                       f.nom             AS fournisseurNom,
                       fi.prix_unitaire  AS prixUnitaire
                FROM FOURNISSEUR_INGREDIENT fi
                JOIN FOURNISSEUR f ON fi.fournisseur_id = f.id
                WHERE fi.ingredient_id = ?
                ORDER BY fi.prix_unitaire ASC
                """;
        return jdbc.query(sql, (rs, i) -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("fournisseurId",  rs.getLong("fournisseurId"));
            m.put("fournisseurNom", rs.getString("fournisseurNom"));
            m.put("prixUnitaire",   rs.getDouble("prixUnitaire"));
            return m;
        }, ingredientId);
    }
 
    public Optional<Map<String, Object>> findMeilleurFournisseur(Long ingredientId) {
        List<Map<String, Object>> prix = findPrixParFournisseur(ingredientId);
        return prix.isEmpty() ? Optional.empty() : Optional.of(prix.get(0));
    }
 
    private Map<String, Object> toMap(ResultSet rs) throws SQLException {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("id",          rs.getLong("id"));
        m.put("nom",         rs.getString("nom"));
        m.put("unite",       rs.getString("unite"));
        m.put("stockActuel", rs.getDouble("stock_actuel"));   // DOUBLE dans le schéma
        m.put("seuilAlerte", rs.getDouble("seuil_alerte"));   // DOUBLE dans le schéma
        return m;
    }
    
}
        
    


