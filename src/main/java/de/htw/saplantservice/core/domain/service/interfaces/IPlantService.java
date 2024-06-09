package de.htw.saplantservice.core.domain.service.interfaces;

import de.htw.saplantservice.core.domain.model.Category;
import de.htw.saplantservice.core.domain.model.Plant;
import de.htw.saplantservice.exception.NoPlantsFoundException;
import de.htw.saplantservice.exception.PlantIdNotFoundException;
import de.htw.saplantservice.exception.PlantsNotFoundByNameException;
import de.htw.saplantservice.exception.PlantsNotFoundInCategoryException;

import java.util.List;

public interface IPlantService {

    void createPlant(Plant plant) throws IllegalArgumentException;
    Plant getPlantById(Long plantId) throws PlantIdNotFoundException, IllegalArgumentException;
    List<Plant> getAllPlants() throws NoPlantsFoundException;
    List<Plant> getPlantsByname(String plantName) throws PlantsNotFoundByNameException,
            IllegalArgumentException;
    List<Plant> getPlantsByCategory(Category plantCategory) throws PlantsNotFoundInCategoryException,
            IllegalArgumentException;
    void updatePlantAmount(Long plantId, Integer newAmount) throws PlantIdNotFoundException,
            IllegalArgumentException;

    void deletePlant(Long plantId) throws PlantIdNotFoundException;
}
