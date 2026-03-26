package fr.epf.restaurant.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.epf.restaurant.model.Client;
@Repository
public class ClientDao {
    private final JdbcTemplate jdbcTemplate;

    public ClientDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Client> findAll() {

        String sql = "SELECT * FROM CLIENT";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Client client = new Client();

            client.setId(rs.getLong("id"));
            client.setNom(rs.getString("nom"));
            client.setPrenom(rs.getString("prenom"));
            client.setEmail(rs.getString("email"));
            client.setTelephone(rs.getString("telephone"));

            return client;
        });
    }

    public void save(Client client) {

        String sql = """
                INSERT INTO CLIENT (nom, prenom, email, telephone)
                VALUES (?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                client.getNom(),
                client.getPrenom(),
                client.getEmail(),
                client.getTelephone()
        );
    }

}
