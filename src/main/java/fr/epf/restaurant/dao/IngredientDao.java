package fr.epf.restaurant.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.epf.restaurant.dto.IngredientDto;
import fr.epf.restaurant.dto.RecommandationsDto;
import fr.epf.restaurant.model.Ingredient;
@Repository
public class IngredientDao {
    private final JdbcTemplate jdbcTemplate;

    public IngredientDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Ingredient> findAll() {

        String sql = "SELECT * FROM INGREDIENT";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Ingredient ingredient=new Ingredient();

            ingredient.setId(rs.getLong("id"));
            ingredient.setNom(rs.getString("nom"));
            ingredient.setUnite(rs.getString("unite"));
            ingredient.setStockActuel(rs.getDouble("stock_actuel"));
            ingredient.setSeuilAlerte(rs.getDouble("seuil_alerte"));
            
            
            return ingredient;
        });
    }

    public List<Ingredient> findByAlert(){
        String sql = "SELECT * FROM INGREDIENT WHERE stock_actuel<seuil_alerte";

        return jdbcTemplate.query(sql,(rs, rowNum) -> {

            Ingredient ingredient=new Ingredient();

            ingredient.setId(rs.getLong("id"));
            ingredient.setNom(rs.getString("nom"));
            ingredient.setUnite(rs.getString("unite"));
            ingredient.setStockActuel(rs.getDouble("stock_actuel"));
            ingredient.setSeuilAlerte(rs.getDouble("seuil_alerte"));
            
            
            return ingredient;
        });

    }

    public List<IngredientDto> findPrixById(long id){

        String sql =
        "SELECT FOURNISSEUR.id, FOURNISSEUR.nom, FOURNISSEUR_INGREDIENT.prix_unitaire " +
        "FROM FOURNISSEUR_INGREDIENT " +
        "JOIN FOURNISSEUR ON FOURNISSEUR_INGREDIENT.fournisseur_id = FOURNISSEUR.id " +
        "WHERE FOURNISSEUR_INGREDIENT.ingredient_id = ?";

        return jdbcTemplate.query(
        sql,
        (rs,rowNum) -> new IngredientDto(
            rs.getLong("id"),
            rs.getString("nom"),
            rs.getDouble("prix_unitaire")
        ),
        id
    );
}

    public RecommandationsDto findRecommandation(long id){

    String sql =
        "SELECT FOURNISSEUR.id, FOURNISSEUR_INGREDIENT.prix_unitaire, FOURNISSEUR.nom " +
        "FROM FOURNISSEUR_INGREDIENT " +
        "JOIN FOURNISSEUR ON FOURNISSEUR.id=FOURNISSEUR_INGREDIENT.fournisseur_id "+
        "WHERE FOURNISSEUR_INGREDIENT.ingredient_id = ? " +
        "ORDER BY prix_unitaire ASC LIMIT 1 ";

    return jdbcTemplate.queryForObject(
        sql,
            (rs,rowNum) -> new RecommandationsDto(
                rs.getString("nom"),
                rs.getLong("id"),
                0,
                rs.getDouble("prix_unitaire")
            ),
            id
        );
}
        
    

}
