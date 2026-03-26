package fr.epf.restaurant.dao;

import fr.epf.restaurant.model.CommandeClient;
import fr.epf.restaurant.dto.LigneCommandeDto;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

@Repository
public class CommandeClientDao {

    private final JdbcTemplate jdbcTemplate;

    public CommandeClientDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    // CREATE COMMANDE
    public long saveCommande(CommandeClient commande){

        String sql = """
        INSERT INTO COMMANDE_CLIENT(client_id,date_commande,statut)
        VALUES(?,?,?)
        """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {

            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setLong(1, commande.getClientId());
            ps.setObject(2, commande.getDateCommande());
            ps.setString(3, commande.getStatut());

            return ps;

        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    // CREATE LIGNE
    public void saveLigne(long commandeId, LigneCommandeDto ligne){

        String sql = """
        INSERT INTO LIGNE_COMMANDE(commande_id,plat_id,quantite)
        VALUES(?,?,?)
        """;

        jdbcTemplate.update(sql,
                commandeId,
                ligne.getPlatId(),
                ligne.getQuantite()
        );
    }

    // GET ALL
    public List<CommandeClient> findAll(){

        String sql = "SELECT * FROM COMMANDE_CLIENT";

        return jdbcTemplate.query(sql,(rs,rowNum)-> new CommandeClient(

                rs.getLong("id"),
                rs.getLong("client_id"),
                rs.getObject("date_commande", LocalDate.class),
                rs.getString("statut")

        ));
    }

    // GET BY ID
    public CommandeClient findById(long id){

        String sql = "SELECT * FROM COMMANDE_CLIENT WHERE id=?";

        List<CommandeClient> result = jdbcTemplate.query(sql,(rs,rowNum)-> new CommandeClient(

                rs.getLong("id"),
                rs.getLong("client_id"),
                rs.getObject("date_commande", LocalDate.class),
                rs.getString("statut")

        ), id);

        if(result.isEmpty()){
            return null;
        }

        return result.get(0);
    }

    // UPDATE STATUT
    public void updateStatut(long id,String statut){

        String sql = """
        UPDATE COMMANDE_CLIENT
        SET statut=?
        WHERE id=?
        """;

        jdbcTemplate.update(sql,statut,id);
    }

    // DELETE
    public void delete(long id){

        String sql = "DELETE FROM COMMANDE_CLIENT WHERE id=?";

        jdbcTemplate.update(sql,id);
    }

}