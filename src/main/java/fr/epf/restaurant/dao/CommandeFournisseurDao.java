package fr.epf.restaurant.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
 
@Repository
public class CommandeFournisseurDao {
 
    private final JdbcTemplate jdbc;
 
    public CommandeFournisseurDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
 
    public List<Map<String, Object>> findAll() {
        return aggregate(jdbc.query(baseSql() + "ORDER BY cf.id", (rs, i) -> toRow(rs)));
    }
 
    public Optional<Map<String, Object>> findById(Long id) {
        List<Map<String, Object>> list = aggregate(
                jdbc.query(baseSql() + "WHERE cf.id = ?", (rs, i) -> toRow(rs), id));
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }
 
    public List<Map<String, Object>> findLignes(Long commandeId) {
        String sql = """
                SELECT ingredient_id      AS ingredientId,
                       quantite_commandee AS quantite
                FROM LIGNE_COMMANDE_FOURNISSEUR
                WHERE commande_fournisseur_id = ?
                """;
        return jdbc.query(sql, (rs, i) -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("ingredientId", rs.getLong("ingredientId"));
            m.put("quantite",     rs.getDouble("quantite"));
            return m;
        }, commandeId);
    }
 
    public Map<String, Object> create(Long fournisseurId) {
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO COMMANDE_FOURNISSEUR (fournisseur_id, statut, date_commande) VALUES (?, 'EN_ATTENTE', ?)",
                    new String[]{"id"});
            ps.setLong(1, fournisseurId);
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            return ps;
        }, kh);
        return findById(kh.getKey().longValue()).orElseThrow();
    }
 
    public void addLigne(Long commandeId, Long ingredientId, double quantite, double prixUnitaire) {
        jdbc.update(
                "INSERT INTO LIGNE_COMMANDE_FOURNISSEUR (commande_fournisseur_id, ingredient_id, quantite_commandee, prix_unitaire) VALUES (?, ?, ?, ?)",
                commandeId, ingredientId, quantite, prixUnitaire);
    }
 
    public void updateStatut(Long id, String statut) {
        jdbc.update("UPDATE COMMANDE_FOURNISSEUR SET statut = ? WHERE id = ?", statut, id);
    }
 
    public void delete(Long id) {
        jdbc.update("DELETE FROM LIGNE_COMMANDE_FOURNISSEUR WHERE commande_fournisseur_id = ?", id);
        jdbc.update("DELETE FROM COMMANDE_FOURNISSEUR WHERE id = ?", id);
    }
 
    // ── Utilitaires ───────────────────────────────────────────────────────────
 
    private String baseSql() {
        return """
                SELECT cf.id                    AS commandeId,
                       cf.statut                AS statut,
                       cf.date_commande         AS dateCommande,
                       f.id                     AS fournisseurId,
                       f.nom                    AS fournisseurNom,
                       f.contact                AS fournisseurContact,
                       f.email                  AS fournisseurEmail,
                       lcf.id                   AS ligneId,
                       lcf.ingredient_id        AS ingredientId,
                       lcf.quantite_commandee   AS quantiteCommandee,
                       lcf.prix_unitaire        AS prixUnitaire,
                       i.nom                    AS ingredientNom,
                       i.unite                  AS ingredientUnite
                FROM COMMANDE_FOURNISSEUR cf
                JOIN FOURNISSEUR f ON cf.fournisseur_id = f.id
                LEFT JOIN LIGNE_COMMANDE_FOURNISSEUR lcf ON cf.id = lcf.commande_fournisseur_id
                LEFT JOIN INGREDIENT i ON lcf.ingredient_id = i.id
                """;
    }
 
    private Map<String, Object> toRow(ResultSet rs) throws SQLException {
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("commandeId",        rs.getLong("commandeId"));
        row.put("statut",            rs.getString("statut"));
        row.put("dateCommande",      rs.getTimestamp("dateCommande"));
        row.put("fournisseurId",     rs.getLong("fournisseurId"));
        row.put("fournisseurNom",    rs.getString("fournisseurNom"));
        row.put("fournisseurContact",rs.getString("fournisseurContact"));
        row.put("fournisseurEmail",  rs.getString("fournisseurEmail"));
        long ligneId = rs.getLong("ligneId");
        row.put("ligneId",           rs.wasNull() ? null : ligneId);
        long ingId = rs.getLong("ingredientId");
        row.put("ingredientId",      rs.wasNull() ? null : ingId);
        row.put("quantiteCommandee", rs.getDouble("quantiteCommandee"));
        row.put("prixUnitaire",      rs.getDouble("prixUnitaire"));
        row.put("ingredientNom",     rs.getString("ingredientNom"));
        row.put("ingredientUnite",   rs.getString("ingredientUnite"));
        return row;
    }
 
    private List<Map<String, Object>> aggregate(List<Map<String, Object>> rows) {
        Map<Long, Map<String, Object>> byId = new LinkedHashMap<>();
        for (Map<String, Object> row : rows) {
            Long cid = (Long) row.get("commandeId");
            byId.computeIfAbsent(cid, k -> {
                // Objet fournisseur imbriqué
                Map<String, Object> fournisseur = new LinkedHashMap<>();
                fournisseur.put("id",      row.get("fournisseurId"));
                fournisseur.put("nom",     row.get("fournisseurNom"));
                fournisseur.put("contact", row.get("fournisseurContact"));
                fournisseur.put("email",   row.get("fournisseurEmail"));
 
                Map<String, Object> c = new LinkedHashMap<>();
                c.put("id",           cid);
                c.put("statut",       row.get("statut"));
                c.put("dateCommande", row.get("dateCommande"));
                c.put("fournisseur",  fournisseur);  // ← objet imbriqué
                c.put("lignes",       new ArrayList<>());
                return c;
            });
            if (row.get("ligneId") != null) {
                Map<String, Object> ligne = new LinkedHashMap<>();
                ligne.put("id",              row.get("ligneId"));
                ligne.put("ingredientId",    row.get("ingredientId"));
                ligne.put("ingredientNom",   row.get("ingredientNom"));
                ligne.put("ingredientUnite", row.get("ingredientUnite"));
                ligne.put("quantite",        row.get("quantiteCommandee"));
                ligne.put("prixUnitaire",    row.get("prixUnitaire"));
                ((List<Map<String, Object>>) byId.get(cid).get("lignes")).add(ligne);
            }
        }
        return new ArrayList<>(byId.values());
    }
}
 