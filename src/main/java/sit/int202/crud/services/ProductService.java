package sit.int202.crud.services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int202.crud.entities.Product;
import sit.int202.crud.repositories.ProductRepository;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public Product findById(String id) {
        return repository.findById(id).orElse(null);
    }

    public Product createProduct(Product product) {
        if(product == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payload is null");

        return repository.save(product);
    }

    public void updateProduct(String id, Product product) {
        if(findById(id) == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The product with this id doesn't existed: " + id);

        repository.save(product);
    }

    public void deleteProduct(String id) {
        repository.deleteById(id);
    }

}
