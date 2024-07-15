package de.htw.saplantservice.core.domain.service.interfaces;

import de.htw.saplantservice.core.domain.model.Category;
import de.htw.saplantservice.core.domain.model.Plant;
import de.htw.saplantservice.port.user.exception.PlantIdNotFoundException;

import java.util.List;
import java.util.UUID;

public interface IPlantService {

    Plant createPlant(Plant plant);
    Plant getPlantById(UUID plantId) throws PlantIdNotFoundException;
    List<Plant> getAllPlants();
    List<Plant> getPlantsByName(String plantName);
    List<Plant> getPlantsByCategory(Category plantCategory);
    Plant updatePlantAmount(UUID plantId, Integer newAmount) throws PlantIdNotFoundException;

    void deletePlant(UUID plantId) throws PlantIdNotFoundException;
    void deleteAllPlants();
}
