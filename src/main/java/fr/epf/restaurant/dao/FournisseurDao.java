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
public class FournisseurDao {

    private final JdbcTemplate jdbc;

    public FournisseurDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Map<String, Object>> findAll() {
        return jdbc.query(
                "SELECT id, nom, contact, email FROM FOURNISSEUR ORDER BY id",
                (rs, i) -> toMap(rs));
    }

    public Optional<Map<String, Object>> findById(Long id) {
        List<Map<String, Object>> rows = jdbc.query(
                "SELECT id, nom, contact, email FROM FOURNISSEUR WHERE id = ?",
                (rs, i) -> toMap(rs), id);
        return rows.isEmpty() ? Optional.empty() : Optional.of(rows.get(0));
    }

    public List<Map<String, Object>> findCatalogue(Long fournisseurId) {
        String sql = "SELECT fi.ingredient_id AS ingredientId,"
                + " i.nom AS ingredientNom,"
                + " i.unite AS ingredientUnite,"
                + " fi.prix_unitaire AS prixUnitaire"
                + " FROM FOURNISSEUR_INGREDIENT fi"
                + " JOIN INGREDIENT i ON fi.ingredient_id = i.id"
                + " WHERE fi.fournisseur_id = ?"
                + " ORDER BY i.nom";
        return jdbc.query(sql, (rs, i) -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("ingredientId", rs.getLong("ingredientId"));
            m.put("ingredientNom", rs.getString("ingredientNom"));
            m.put("ingredientUnite", rs.getString("ingredientUnite"));
            m.put("prixUnitaire", rs.getDouble("prixUnitaire"));
            return m;
        }, fournisseurId);
    }

    public Map<String, Object> create(String nom, String contact, String email) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO FOURNISSEUR (nom, contact, email) VALUES (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, nom);
            ps.setString(2, contact != null ? contact : "");
            ps.setString(3, email != null ? email : "");
            return ps;
        }, keyHolder);
        return findById(keyHolder.getKey().longValue()).orElseThrow();
    }

    private Map<String, Object> toMap(ResultSet rs) throws SQLException {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("id", rs.getLong("id"));
        m.put("nom", rs.getString("nom"));
        m.put("contact", rs.getString("contact"));
        m.put("email", rs.getString("email"));
        return m;
    }
}