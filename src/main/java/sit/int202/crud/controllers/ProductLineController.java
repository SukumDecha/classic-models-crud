package sit.int202.crud.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sit.int202.crud.entities.Productline;
import sit.int202.crud.services.ProductLineService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/productlines")
@AllArgsConstructor
public class ProductLineController {

    private ProductLineService service;

    @GetMapping({"", "/"})
    public String view(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int pageSize,
                       @RequestParam(defaultValue = "") String query,
                       Model model) {
        Page<Productline> productlineList = service.findByQuery(query,  page - 1, pageSize); // Spring Data starts at page 0

        model.addAttribute("productlines", productlineList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productlineList.getTotalPages());
        model.addAttribute("pageSize", pageSize);

        model.addAttribute("productLine", null);
        model.addAttribute("editMode", false);

        return "productlines/get";
    }

    @GetMapping("/create")
    public String createProductLineGet(Model model) {
        Productline productline = new Productline();

        Page<Productline> productlineList = service.findByQuery("",  0, 5);

        model.addAttribute("productlines", productlineList);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", productlineList.getTotalPages());
        model.addAttribute("pageSize", 5);

        model.addAttribute("productLine", productline);
        model.addAttribute("editMode", false);

        return "productlines/get";
    }

    @PostMapping("/create")
    public void createProductLinePost(Productline pl,HttpServletResponse response) throws IOException {
        try {
            service.createProductline(pl);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        response.sendRedirect("/productlines");
    }

    @GetMapping("/edit")
    public String editProductLineGet(@RequestParam("productLine") String id , Model model) {
        Productline productline = service.findById(id);

        Page<Productline> productlineList = service.findByQuery("",  0, 5);

        model.addAttribute("productlines", productlineList);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", productlineList.getTotalPages());
        model.addAttribute("pageSize", 5);

        model.addAttribute("productLine", productline);
        model.addAttribute("editMode", true);

        return "productlines/get";
    }

    @PostMapping("/edit")
    public void editProductLinePost(@RequestParam("productLine") String productLine, Productline productline,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws IOException {
        service.updateProductLine(productLine, productline);



        response.sendRedirect("/productlines");
    }

    @GetMapping("/delete")
    public String deleteProductLine(@RequestParam("productLine") String id) {
        try {
            service.removeProductLine(id);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/productlines";
        }

        return "redirect:/productlines";

    }

}
