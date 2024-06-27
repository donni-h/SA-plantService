package de.htw.saplantservice.core.domain.service.interfaces;

import de.htw.saplantservice.core.domain.model.Category;
import de.htw.saplantservice.core.domain.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPlantRepository extends JpaRepository<Plant, Long> {
    List<Plant> findByName(String plantName);
    List<Plant> findByCategory(Category plantCategory);
}
