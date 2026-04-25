package fr.epf.restaurant.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


@Repository
public class ClientDao {
    private final JdbcTemplate jdbc;
 
    public ClientDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
 
    public List<Map<String, Object>> findAll() {
        return jdbc.query(
                "SELECT id, nom, prenom, email, telephone FROM CLIENT ORDER BY id",
                (rs, i) -> toMap(rs));
    }
 
    public Optional<Map<String, Object>> findById(Long id) {
        List<Map<String, Object>> rows = jdbc.query(
                "SELECT id, nom, prenom, email, telephone FROM CLIENT WHERE id = ?",
                (rs, i) -> toMap(rs), id);
        return rows.isEmpty() ? Optional.empty() : Optional.of(rows.get(0));
    }
 
    public Map<String, Object> create(String nom, String prenom, String email, String telephone) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO CLIENT (nom, prenom, email, telephone) VALUES (?, ?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, nom);
            ps.setString(2, prenom);
            ps.setString(3, email);
            ps.setString(4, telephone != null ? telephone : "");
            return ps;
        }, keyHolder);
        return findById(keyHolder.getKey().longValue()).orElseThrow();
    }
 
    /** RowMapper explicite : garantit des clés en minuscules quelle que soit la BDD */
    private Map<String, Object> toMap(ResultSet rs) throws SQLException {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("id", rs.getLong("id"));
        m.put("nom", rs.getString("nom"));
        m.put("prenom", rs.getString("prenom"));
        m.put("email", rs.getString("email"));
        m.put("telephone", rs.getString("telephone"));
        return m;
    }

    

}
