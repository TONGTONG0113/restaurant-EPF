package fr.epf.restaurant.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;
 
@Repository
public class PlatDao {
 
    private final JdbcTemplate jdbc;
 
    public PlatDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
 
    public List<Map<String, Object>> findAll() {
        return aggregate(jdbc.query(buildSql(false), (rs, i) -> toRow(rs)));
    }
 
    public Optional<Map<String, Object>> findById(Long id) {
        List<Map<String, Object>> list = aggregate(
                jdbc.query(buildSql(true), (rs, i) -> toRow(rs), id));
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }
 
    public Map<String, Object> create(String nom, String description, double prix) {
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO PLAT (nom, description, prix) VALUES (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, nom);
            ps.setString(2, description);
            ps.setDouble(3, prix);
            return ps;
        }, kh);
        return findById(kh.getKey().longValue()).orElseThrow();
    }
 
    public void addIngredient(Long platId, Long ingredientId, double quantite) {
        jdbc.update(
                "INSERT INTO PLAT_INGREDIENT (plat_id, ingredient_id, quantite_requise) VALUES (?, ?, ?)",
                platId, ingredientId, quantite);
    }
 
    // ── Utilitaires ───────────────────────────────────────────────────────────
 
    private String buildSql(boolean withWhere) {
        return """
                SELECT p.id               AS platId,
                       p.nom              AS platNom,
                       p.description      AS platDescription,
                       p.prix             AS platPrix,
                       pi.ingredient_id   AS ingredientId,
                       i.nom              AS ingredientNom,
                       i.unite            AS ingredientUnite,
                       pi.quantite_requise AS quantiteRequise
                FROM PLAT p
                LEFT JOIN PLAT_INGREDIENT pi ON p.id = pi.plat_id
                LEFT JOIN INGREDIENT i       ON pi.ingredient_id = i.id
                """ + (withWhere ? "WHERE p.id = ? " : "") + "ORDER BY p.id";
    }
 
    private Map<String, Object> toRow(ResultSet rs) throws SQLException {
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("platId",          rs.getLong("platId"));
        row.put("platNom",         rs.getString("platNom"));
        row.put("platDescription", rs.getString("platDescription"));
        row.put("platPrix",        rs.getDouble("platPrix"));
        long ingId = rs.getLong("ingredientId");
        row.put("ingredientId",    rs.wasNull() ? null : ingId);
        row.put("ingredientNom",   rs.getString("ingredientNom"));
        row.put("ingredientUnite", rs.getString("ingredientUnite"));
        row.put("quantiteRequise", rs.getDouble("quantiteRequise"));
        return row;
    }
 
    private List<Map<String, Object>> aggregate(List<Map<String, Object>> rows) {
        Map<Long, Map<String, Object>> byId = new LinkedHashMap<>();
        for (Map<String, Object> row : rows) {
            Long platId = (Long) row.get("platId");
            byId.computeIfAbsent(platId, k -> {
                Map<String, Object> plat = new LinkedHashMap<>();
                plat.put("id",          platId);
                plat.put("nom",         row.get("platNom"));
                plat.put("description", row.get("platDescription"));
                plat.put("prix",        row.get("platPrix"));
                plat.put("ingredients", new ArrayList<>());
                return plat;
            });
            if (row.get("ingredientId") != null) {
                Map<String, Object> ing = new LinkedHashMap<>();
                ing.put("ingredientId",    row.get("ingredientId"));
                ing.put("ingredientNom",   row.get("ingredientNom"));
                ing.put("ingredientUnite", row.get("ingredientUnite"));
                ing.put("quantiteRequise", row.get("quantiteRequise"));
                ((List<Map<String, Object>>) byId.get(platId).get("ingredients")).add(ing);
            }
        }
        return new ArrayList<>(byId.values());
    }
}