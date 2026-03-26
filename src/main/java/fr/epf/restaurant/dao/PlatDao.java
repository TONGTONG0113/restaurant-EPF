package fr.epf.restaurant.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.epf.restaurant.model.Plat;

@Repository
public class PlatDao {
    private final JdbcTemplate jdbcTemplate;

    public PlatDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Plat> findAll() {

        String sql = "SELECT * FROM PLAT";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Plat plat = new Plat();

            plat.setId(rs.getLong("id"));
            plat.setNom(rs.getString("nom"));
            plat.setDescription(rs.getString("description"));
            plat.setPrix(rs.getDouble("prix"));
            
            
            return plat;
        });
    }

    public Plat findById(Long id){
        String sql = "SELECT * FROM PLAT WHERE id=?";

        return jdbcTemplate.queryForObject(sql,(rs, rowNum) -> {
            Plat plat = new Plat();

            plat.setId(rs.getLong("id"));
            plat.setNom(rs.getString("nom"));
            plat.setDescription(rs.getString("description"));
            plat.setPrix(rs.getDouble("prix"));
            
            return plat;
        },
        id
        );

    }

    public void save(Plat plat) {

        String sql = """
                INSERT INTO PLAT (nom, description, prix)
                VALUES (?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                plat.getNom(),
                plat.getDescription(),
                plat.getPrix()
                
        );
    }



}
