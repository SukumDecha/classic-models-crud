package sit.int202.crud.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sit.int202.crud.entities.Productline;

import java.util.List;
import java.util.Optional;

public interface ProductLineRepository extends JpaRepository<Productline, String> {

    @Query("select pl from Productline pl " +
            "where pl.htmlDescription like %?1% " +
            "or pl.productLine like %?1% " +
            "or pl.textDescription like %?1%")
    Page<Productline> findByQuery(String query, Pageable pageable);
}
