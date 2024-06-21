package de.htw.saplantservice.core.domain.service.interfaces;

import de.htw.saplantservice.core.domain.model.Category;
import de.htw.saplantservice.core.domain.model.Plant;
import de.htw.saplantservice.port.user.exception.PlantIdNotFoundException;

import java.util.List;

public interface IPlantService {

    void createPlant(Plant plant) throws IllegalArgumentException;
    Plant getPlantById(Long plantId) throws PlantIdNotFoundException, IllegalArgumentException;
    List<Plant> getAllPlants();
    List<Plant> getPlantsByname(String plantName) throws IllegalArgumentException;
    List<Plant> getPlantsByCategory(Category plantCategory) throws IllegalArgumentException;
    void updatePlantAmount(Long plantId, Integer newAmount) throws PlantIdNotFoundException,
            IllegalArgumentException;

    void deletePlant(Long plantId) throws PlantIdNotFoundException;
}
