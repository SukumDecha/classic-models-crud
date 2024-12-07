package sit.int202.crud.services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int202.crud.entities.Product;
import sit.int202.crud.entities.Productline;
import sit.int202.crud.repositories.ProductLineRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductLineService {

    private final ProductLineRepository repository;
    private final ProductService productService;

    public Productline findById(String id) {
        return repository.findById(id).orElse(null);
    }

    public List<Productline> findAll() {
        return repository.findAll();
    }

    public List<Productline> findByQuery(String query) {
        return repository.findByQuery(query).orElseThrow(null);
    }

    public Productline createProductline(Productline productline) {
        return repository.save(productline);
    }

    public void removeProductLine(String id) {
        Productline pl = findById(id);

        if(pl == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The product line with this id doesn't existed: " + id);

        List<Product> productList = pl.getProducts().stream().toList();

        productList.forEach((p) -> {
            p.setProductLine(null);

            productService.updateProduct(p.getProductCode(), p);
        });

        repository.deleteById(id);
    }

    public void updateProductLine(String id, Productline productline) {
        if(findById(id) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Product line with this line is already existed: " + productline.getProductLine());
        }

        repository.save(productline);
    }
}
