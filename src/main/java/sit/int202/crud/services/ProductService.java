package sit.int202.crud.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<Product> findByQuery(String query, int page , int size ) {
        Pageable pageable = PageRequest.of(page, size);

        if(query.isEmpty()) {
            return repository.findAll(pageable);
        }

        return repository.findByQuery(query, pageable);
    }

//    public Page<Product> findByPriceRange(double start, double end) {
//        return repository.findProductByBuyPriceBetween(start, end);
//    }

    public Product createProduct(Product product) {
        if(product == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payload is null");

        return repository.save(product);
    }

    public void updateProduct(String id, Product product) {
        if(findById(id) == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The product with this id doesn't existed: " + id);

        repository.save(product);
    }

    public void deleteProductById(String id) {
        repository.deleteById(id);
    }

}
