package de.htw.saplantservice.core.domain.service.impl;

import de.htw.saplantservice.core.domain.model.Category;
import de.htw.saplantservice.core.domain.model.Plant;
import de.htw.saplantservice.core.domain.service.interfaces.IPlantRepository;
import de.htw.saplantservice.core.domain.service.interfaces.IPlantService;
import de.htw.saplantservice.port.user.exception.PlantIdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PlantService implements IPlantService {
    private final IPlantRepository plantRepository;

    @Autowired
    public PlantService(IPlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    @Override
    public Plant createPlant(Plant plant){
        return plantRepository.save(plant);
    }

    @Override
    public Plant getPlantById(UUID plantId) throws PlantIdNotFoundException {
        return plantRepository.findById(plantId)
                .orElseThrow(() -> new PlantIdNotFoundException(plantId));
    }

    @Override
    public List<Plant> getAllPlants(){
        List<Plant> plants = plantRepository.findAll();
        return plants;
    }

    @Override
    public List<Plant> getPlantsByName(String plantName){
        List<Plant> plants = plantRepository.findByName(plantName);
        return plants;
    }

    @Override
    public List<Plant> getPlantsByCategory(Category plantCategory){
        List<Plant> plants = plantRepository.findByCategory(plantCategory);
        return plants;
    }

    @Transactional
    @Override
    public Plant updatePlantAmount(UUID plantId, Integer newAmount) throws PlantIdNotFoundException{
        Plant existingPlant = plantRepository.findById(plantId)
                .orElseThrow(() -> new PlantIdNotFoundException(plantId));
        existingPlant.setAmount(newAmount);
        return existingPlant;
    }

    @Transactional
    @Override
    public Plant updatePlantAmountWithDifference(UUID plantId, Integer difference) throws PlantIdNotFoundException {
        Plant existingPlant = getPlantById(plantId);
        existingPlant.setAmount(existingPlant.getAmount()+difference);
        return existingPlant;
    }

    @Override
    public void deletePlant(UUID plantId) throws PlantIdNotFoundException{
        if(!plantRepository.existsById(plantId)) throw new PlantIdNotFoundException(plantId);
        plantRepository.deleteById(plantId);
    }

    @Override
    public void deleteAllPlants(){
        plantRepository.deleteAll();
    }
}
