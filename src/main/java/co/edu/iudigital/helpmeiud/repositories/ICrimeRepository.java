package co.edu.iudigital.helpmeiud.repositories;

import co.edu.iudigital.helpmeiud.models.Crime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICrimeRepository extends JpaRepository<Crime, Long> {

    Crime findByName(String name);
    Crime findByNameIgnoreCase(String name);

}
