package fr.epf.restaurant.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.epf.restaurant.dto.FournisseurDto;
import fr.epf.restaurant.model.Fournisseur;


@Repository
public class FournisseurDao {
    private final JdbcTemplate jdbcTemplate;

    public FournisseurDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    public List<Fournisseur> findAll(){
        String sql = "SELECT * FROM FOURNISSEUR";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Fournisseur fournisseur =new Fournisseur();

            fournisseur.setId(rs.getLong("id"));
            fournisseur.setNom(rs.getString("nom"));
            fournisseur.setContact(rs.getString("contact"));
            fournisseur.setEmail(rs.getString("email"));

            
            return fournisseur;
        });  
    }

    public List<FournisseurDto> findPrixById(long id){
        String sql= """
        SELECT FOURNISSEUR.id, FOURNISSEUR.nom, FOURNISSEUR_INGREDIENT.prix_unitaire,
        INGREDIENT.nom, FOURNISSEUR_INGREDIENT.ingredient_id, INGREDIENT.unite
        FROM FOURNISSEUR_INGREDIENT
        JOIN FOURNISSEUR ON FOURNISSEUR.id=FOURNISSEUR_INGREDIENT.fournisseur_id
        JOIN INGREDIENT ON INGREDIENT.id=FOURNISSEUR_INGREDIENT.ingredient_id
        WHERE FOURNISSEUR_INGREDIENT.fournisseur_id=?
                """;

        return jdbcTemplate.query(sql, (rs,rowNum)-> new FournisseurDto(
            rs.getLong("id"),
            rs.getString("nom"),
            rs.getDouble("prix_unitaire"),
            rs.getString("nom"),
            rs.getLong("ingredient_id"),
            rs.getString("unite")
        ),
        id);
    }




}
