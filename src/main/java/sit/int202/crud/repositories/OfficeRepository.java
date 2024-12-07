package sit.int202.crud.repositories;

import sit.int202.crud.entities.Office;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OfficeRepository extends JpaRepository<Office, String> {

    public Optional<List<Office>> findByCity(String city);

    public Optional<List<Office>> findAllByCityContainingOrStateContainingOrCountryContaining(String city, String state, String country);

    public Optional<Office> findAllByCityContaining(String city);

}
