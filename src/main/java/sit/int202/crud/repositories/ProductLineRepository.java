package sit.int202.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sit.int202.crud.entities.Productline;

import java.util.List;
import java.util.Optional;

public interface ProductLineRepository extends JpaRepository<Productline, String> {

    public Optional<List<Productline>> findAllByProductLine(String productLine);

    @Query("select pl from Productline pl " +
            "where pl.htmlDescription like %?1% " +
            "or pl.productLine like %?1% " +
            "or pl.textDescription like %?1%")
    Optional<List<Productline>> findByQuery(String query);
}
