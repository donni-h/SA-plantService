package de.htw.saplantservice.core.domain.service.interfaces;

import de.htw.saplantservice.core.domain.model.Category;
import de.htw.saplantservice.core.domain.model.Plant;
import de.htw.saplantservice.port.user.exception.NoPlantsFoundException;
import de.htw.saplantservice.port.user.exception.PlantIdNotFoundException;

import java.util.List;

public interface IPlantService {

    void createPlant(Plant plant) throws IllegalArgumentException;
    Plant getPlantById(Long plantId) throws PlantIdNotFoundException, IllegalArgumentException;
    List<Plant> getAllPlants() throws NoPlantsFoundException;
    List<Plant> getPlantsByname(String plantName) throws NoPlantsFoundException,
            IllegalArgumentException;
    List<Plant> getPlantsByCategory(Category plantCategory) throws NoPlantsFoundException,
            IllegalArgumentException;
    void updatePlantAmount(Long plantId, Integer newAmount) throws PlantIdNotFoundException,
            IllegalArgumentException;

    void deletePlant(Long plantId) throws PlantIdNotFoundException;
}
