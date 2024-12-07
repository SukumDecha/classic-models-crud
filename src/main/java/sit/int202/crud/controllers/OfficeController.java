package sit.int202.crud.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sit.int202.crud.entities.Office;
import org.springframework.stereotype.Controller;
import sit.int202.crud.services.OfficeService;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/offices")
@RequiredArgsConstructor
public class OfficeController {

    private final OfficeService service;

    @GetMapping()
    public String getOffice(Model model) {
        List<Office> offices = service.findAll();
        Set<String> cities = service.findAllCities();

        model.addAttribute("offices", offices);
        model.addAttribute("cities", cities);
        return "offices/get";
    }

    @GetMapping("/search")
    public String searchByKeywords(@RequestParam String keyword, Model model) {
        List<Office> offices = service.findByKeyword(keyword);
        Set<String> cities = service.findAllCities();

        model.addAttribute("offices", offices);
        model.addAttribute("cities", cities);
        return "offices/get";
    }

    @GetMapping("/searchByCity")
    public String searchByCity(@RequestParam String city, Model model) {
        List<Office> offices = service.findByCity(city);
        Set<String> cities = service.findAllCities();

        model.addAttribute("offices", offices);
        model.addAttribute("cities", cities);
        return "offices/get";
    }


    @GetMapping("/create")
    public String createGet() {
        return "offices/create";
    }

    @PostMapping("/create")
    public String create(Office office, Model model) {
        Office createdOffice = service.createOffice(office);

        model.addAttribute("office", createdOffice);
        return "redirect:/offices";
    }

    @GetMapping("/update")
    public String updateGet(@RequestParam String officeId, Model model) {
        Office office;
        try {
            office = service.findById(officeId);
            model.addAttribute("office", office);
            return "offices/update";
        } catch (Exception e) {
            return "offices/get";
        }
    }

    @PostMapping("/update")
    public String update(Office office, Model model) {
        service.updateOffice(office.getOfficeCode(), office);

        model.addAttribute("office", office);
        model.addAttribute("status", "Office updated!");
        return "offices/update";
    }


}
