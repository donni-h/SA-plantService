package de.htw.saplantservice.core.domain.service.interfaces;

import de.htw.saplantservice.core.domain.model.Plant;

public interface IPlantService {

    void createPlant(Plant plant);

    void updatePlant(Plant plant);

    void deletePlant(long id);

    Iterable<Plant> getAllPlants();
}
