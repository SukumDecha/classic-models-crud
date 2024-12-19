package sit.int202.crud.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sit.int202.crud.entities.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

    @Query("select p from Product p " +
            "where p.productName like %?1% " +
            "or p.productDescription like %?1%")
    Page<Product> findByQuery(String query, Pageable pageable);

}
