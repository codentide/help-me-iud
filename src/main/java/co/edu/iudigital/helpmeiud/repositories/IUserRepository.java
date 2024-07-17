package co.edu.iudigital.helpmeiud.repositories;

import co.edu.iudigital.helpmeiud.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
