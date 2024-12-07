package sit.int202.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int202.crud.entities.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
}
