package co.edu.iudigital.helpmeiud.repositories;

import co.edu.iudigital.helpmeiud.models.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICaseRepository extends JpaRepository<Case, Long> {

    List<Case> findAllByUserIdUsername(String username);
    List<Case> findAllByIsVisibleTrue();
    List<Case> findAllByIsVisibleFalse();
    @Modifying
    @Query("UPDATE Case c SET c.isVisible = :visibility WHERE c.id = :id")
    void updateVisibility(@Param("visibility") Boolean visibility, @Param("id") Long id);


}
