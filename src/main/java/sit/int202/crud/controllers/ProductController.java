package sit.int202.crud.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sit.int202.crud.entities.Product;
import sit.int202.crud.services.ProductLineService;
import sit.int202.crud.services.ProductService;

import java.io.IOException;

@Controller
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private ProductService service;
    private ProductLineService productLineService;

    @GetMapping({"", "/"})
    public String view(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int pageSize,
                       @RequestParam(defaultValue = "") String query,
                       Model model) {
        Page<Product> products = service.findByQuery(query,  page - 1, pageSize); // Spring Data starts at page 0

        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("pageSize", pageSize);


        model.addAttribute("product", null);
        model.addAttribute("productLines", null);
        model.addAttribute("editMode", false);

        return "products/get";
    }

    @GetMapping("/create")
    public String createProductLineGet(Model model) {
        Product product = new Product();

        Page<Product> products = service.findByQuery("",  0, 5);

        model.addAttribute("products", products);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("pageSize", 5);

        System.out.println("Product " + product);
        System.out.println("Product lines " + productLineService.findAll());

        model.addAttribute("product", product);
        model.addAttribute("productLines", productLineService.findAll());
        model.addAttribute("editMode", false);

        return "products/get";
    }

    @PostMapping("/create")
    public void createProductLinePost(Product product,HttpServletResponse response) throws IOException {
        try {
            service.createProduct(product);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        response.sendRedirect("/products");
    }

    @GetMapping("/edit")
    public String editProductLineGet(@RequestParam("productCode") String productCode , Model model) {
        Product product = service.findById(productCode);

        Page<Product> products = service.findByQuery("",  0, 5);

        model.addAttribute("products", products);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("pageSize", 5);

        model.addAttribute("product", product);
        model.addAttribute("productLines", productLineService.findAll());
        model.addAttribute("editMode", true);

        return "products/get";
    }

    @PostMapping("/edit")
    public void editProductLinePost(@RequestParam("productCode") String productCode, Product product, HttpServletResponse response) throws IOException {
        service.updateProduct(productCode, product);

        response.sendRedirect("/products");
    }

    @GetMapping("/delete")
    public String deleteProductLine(@RequestParam("productCode") String productCode) {
        try {
            service.deleteProductById(productCode);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/products";
        }

        return "redirect:/products";
    }

}
