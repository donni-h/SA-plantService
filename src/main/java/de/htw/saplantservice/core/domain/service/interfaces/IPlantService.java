package de.htw.saplantservice.core.domain.service.interfaces;

import de.htw.saplantservice.core.domain.model.Category;
import de.htw.saplantservice.core.domain.model.Plant;
import de.htw.saplantservice.port.user.exception.PlantIdAlreadyExistsException;
import de.htw.saplantservice.port.user.exception.PlantIdNotFoundException;

import java.util.List;

public interface IPlantService {

    void createPlant(Plant plant);
    Plant getPlantById(Long plantId) throws PlantIdNotFoundException;
    List<Plant> getAllPlants();
    List<Plant> getPlantsByName(String plantName);
    List<Plant> getPlantsByCategory(Category plantCategory);
    void updatePlantAmount(Long plantId, Integer newAmount) throws PlantIdNotFoundException;

    void deletePlant(Long plantId) throws PlantIdNotFoundException;
}
