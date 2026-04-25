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
public class CommandeClientDao {
 
   private final JdbcTemplate jdbc;
 
    public CommandeClientDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
 
    public List<Map<String, Object>> findAll(String statut) {
        String where = (statut != null && !statut.isBlank())
                ? "WHERE cc.statut = '" + statut.toUpperCase() + "' " : "";
        return aggregate(jdbc.query(baseSql() + where + "ORDER BY cc.id", (rs, i) -> toRow(rs)));
    }
 
    public Optional<Map<String, Object>> findById(Long id) {
        List<Map<String, Object>> list = aggregate(
                jdbc.query(baseSql() + "WHERE cc.id = ?", (rs, i) -> toRow(rs), id));
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }
 
    public List<Map<String, Object>> findBesoinsIngredients(Long commandeId) {
        String sql = """
                SELECT lcc.quantite          AS quantitePlat,
                       pi.ingredient_id      AS ingredientId,
                       pi.quantite_requise   AS quantiteRequise
                FROM LIGNE_COMMANDE_CLIENT lcc
                JOIN PLAT_INGREDIENT pi ON lcc.plat_id = pi.plat_id
                WHERE lcc.commande_client_id = ?
                """;
        return jdbc.query(sql, (rs, i) -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("ingredientId",    rs.getLong("ingredientId"));
            m.put("quantitePlat",    rs.getInt("quantitePlat"));
            m.put("quantiteRequise", rs.getDouble("quantiteRequise"));
            return m;
        }, commandeId);
    }
 
    public Map<String, Object> create(Long clientId) {
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO COMMANDE_CLIENT (client_id, statut, date_commande) VALUES (?, 'EN_ATTENTE', ?)",
                    new String[]{"id"});   // ← spécifie uniquement la colonne id
            ps.setLong(1, clientId);
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            return ps;
        }, kh);
        return findById(kh.getKey().longValue()).orElseThrow();
    }
 
    public void addLigne(Long commandeId, Long platId, int quantite) {
        jdbc.update(
                "INSERT INTO LIGNE_COMMANDE_CLIENT (commande_client_id, plat_id, quantite) VALUES (?, ?, ?)",
                commandeId, platId, quantite);
    }
 
    public void updateStatut(Long id, String statut) {
        jdbc.update("UPDATE COMMANDE_CLIENT SET statut = ? WHERE id = ?", statut, id);
    }
 
    public void delete(Long id) {
        jdbc.update("DELETE FROM LIGNE_COMMANDE_CLIENT WHERE commande_client_id = ?", id);
        jdbc.update("DELETE FROM COMMANDE_CLIENT WHERE id = ?", id);
    }
 
    private String baseSql() {
        return """
                SELECT cc.id            AS commandeId,
                       cc.statut        AS statut,
                       cc.date_commande AS dateCommande,
                       c.id             AS clientId,
                       c.nom            AS clientNom,
                       c.prenom         AS clientPrenom,
                       c.email          AS clientEmail,
                       c.telephone      AS clientTelephone,
                       lcc.id           AS ligneId,
                       lcc.plat_id      AS platId,
                       lcc.quantite     AS quantite,
                       p.nom            AS platNom,
                       p.prix           AS platPrix
                FROM COMMANDE_CLIENT cc
                JOIN CLIENT c ON cc.client_id = c.id
                LEFT JOIN LIGNE_COMMANDE_CLIENT lcc ON cc.id = lcc.commande_client_id
                LEFT JOIN PLAT p ON lcc.plat_id = p.id
                """;
    }
 
    private Map<String, Object> toRow(ResultSet rs) throws SQLException {
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("commandeId",      rs.getLong("commandeId"));
        row.put("statut",          rs.getString("statut"));
        row.put("dateCommande",    rs.getTimestamp("dateCommande"));
        row.put("clientId",        rs.getLong("clientId"));
        row.put("clientNom",       rs.getString("clientNom"));
        row.put("clientPrenom",    rs.getString("clientPrenom"));
        row.put("clientEmail",     rs.getString("clientEmail"));
        row.put("clientTelephone", rs.getString("clientTelephone"));
        long ligneId = rs.getLong("ligneId");
        row.put("ligneId",  rs.wasNull() ? null : ligneId);
        long platId = rs.getLong("platId");
        row.put("platId",   rs.wasNull() ? null : platId);
        row.put("quantite", rs.getInt("quantite"));
        row.put("platNom",  rs.getString("platNom"));
        row.put("platPrix", rs.getDouble("platPrix"));
        return row;
    }
 
    private List<Map<String, Object>> aggregate(List<Map<String, Object>> rows) {
        Map<Long, Map<String, Object>> byId = new LinkedHashMap<>();
        for (Map<String, Object> row : rows) {
            Long cid = (Long) row.get("commandeId");
            byId.computeIfAbsent(cid, k -> {
                Map<String, Object> client = new LinkedHashMap<>();
                client.put("id",        row.get("clientId"));
                client.put("nom",       row.get("clientNom"));
                client.put("prenom",    row.get("clientPrenom"));
                client.put("email",     row.get("clientEmail"));
                client.put("telephone", row.get("clientTelephone"));
                Map<String, Object> c = new LinkedHashMap<>();
                c.put("id",           cid);
                c.put("statut",       row.get("statut"));
                c.put("dateCommande", row.get("dateCommande"));
                c.put("client",       client);
                c.put("lignes",       new ArrayList<>());
                return c;
            });
            if (row.get("ligneId") != null) {
                Map<String, Object> ligne = new LinkedHashMap<>();
                ligne.put("id",       row.get("ligneId"));
                ligne.put("platId",   row.get("platId"));
                ligne.put("platNom",  row.get("platNom"));
                ligne.put("platPrix", row.get("platPrix"));
                ligne.put("quantite", row.get("quantite"));
                ((List<Map<String, Object>>) byId.get(cid).get("lignes")).add(ligne);
            }
        }
        return new ArrayList<>(byId.values());
    }
}