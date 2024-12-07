package sit.int202.crud.services;


import sit.int202.crud.entities.Office;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int202.crud.repositories.OfficeRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfficeService {

    private final OfficeRepository repository;

    public List<Office> findAll() {
        return repository.findAll();
    }

    public Set<String> findAllCities() {
        List<Office> offices = findAll();

        return offices.stream().map(Office::getCity).collect(Collectors.toSet());
    }

    public List<Office> findByKeyword(String keyword) {
        return repository.findAllByCityContainingOrStateContainingOrCountryContaining(keyword, keyword, keyword)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Office found"));
    }

    public Office findById(String id) {
        return repository.findById(id) .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Office not found"));
    }

    public List<Office> findByCity(String city) {
        return repository.findByCity(city)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Office not found"));
    }

    public Office createOffice(Office office) {
        return repository.save(office);
    }

    public void updateOffice(String id, Office updatedOffice) {
        Office office = findById(id);

        if(office == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Office not found: " + id);

        repository.save(updatedOffice);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
