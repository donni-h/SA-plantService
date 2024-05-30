package de.htw.saplantservice.core.domain.service.interfaces;

import de.htw.saplantservice.core.domain.model.Plant;
import org.springframework.data.repository.CrudRepository;

public interface IPlantRepository extends CrudRepository<Plant, Long> {

}
