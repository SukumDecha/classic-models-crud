package sit.int202.crud.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sit.int202.crud.entities.Productline;
import sit.int202.crud.services.ProductLineService;

import java.util.List;

@Controller
@RequestMapping("/productlines")
@AllArgsConstructor
public class ProductLineController {

    private ProductLineService service;

    public Model buildView(Model model, Productline productline, boolean editMode) {
        List<Productline> productlineList = service.findAll();
        model.addAttribute("productlines", productlineList);

        model.addAttribute("productLine", productline);
        model.addAttribute("editMode", editMode);
        return model;
    }

    public Model buildView(Model model, List<Productline> productlineList) {
        model.addAttribute("productlines", productlineList);

        model.addAttribute("productLine", null);
        model.addAttribute("editMode", false);
        return model;
    }

    @GetMapping({ "", "/"})
    public String view(Model model) {
        buildView(model, null, false);

        return "productlines/get";
    }

    @GetMapping("/search")
    public String view(@RequestParam("query") String query, Model model) {
        List<Productline> productlineList = service.findByQuery(query);
        buildView(model, productlineList);

        return "productlines/get";
    }

    @GetMapping("/create")
    public String createProductLineGet(Model model) {
        Productline productline = new Productline();

        buildView(model, productline, false);
        return "productlines/get";
    }

    @PostMapping("/create")
    public String createProductLinePost(Productline pl, Model model) {
        try {
            service.createProductline(pl);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return view(model);
    }

    @GetMapping("/edit")
    public String editProductLineGet(@RequestParam("productLine") String id , Model model) {
        Productline productline = service.findById(id);

        buildView(model, productline, true);
        return "productlines/get";
    }

    @PostMapping("/edit")
    public String editProductLinePost(@RequestParam("productLine") String productLine, Productline productline, Model model) {
        service.updateProductLine(productLine, productline);

        return "redirect:/productlines";
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
