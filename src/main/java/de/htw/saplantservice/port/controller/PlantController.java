package de.htw.saplantservice.port.controller;

import de.htw.saplantservice.core.domain.model.Category;
import de.htw.saplantservice.core.domain.model.Plant;
import de.htw.saplantservice.core.domain.service.interfaces.IPlantService;
import de.htw.saplantservice.port.user.exception.NoPlantsFoundException;
import de.htw.saplantservice.port.user.exception.PlantIdAlreadyExistsException;
import de.htw.saplantservice.port.user.exception.PlantIdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlantController {
    private final IPlantService plantService;
    @Autowired
    public PlantController(IPlantService plantService){
        this.plantService = plantService;
    }

    @PostMapping(path = "/plant")
    @ResponseStatus(HttpStatus.OK)
    public void createPlant(@RequestBody Plant plant) throws PlantIdAlreadyExistsException {
        plantService.createPlant(plant);
    }

    @GetMapping(path = "/plant/{plantId}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Plant getPlantById(@PathVariable("plantId") Long plantId) throws PlantIdNotFoundException,
            IllegalArgumentException {
        return plantService.getPlantById(plantId);
    }

    @GetMapping(path = "/plants")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Plant> getAllPlants() throws NoPlantsFoundException {
        return plantService.getAllPlants();
    }

    @GetMapping(path = "/plants/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Plant> getPlantsByName(@PathVariable("name") String plantName) throws
            NoPlantsFoundException, IllegalArgumentException{
        return plantService.getPlantsByname(plantName);
    }

    @GetMapping(path = "/plants/category/{category}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Plant> getPlantsByCategory(@PathVariable("category") Category plantCategory) throws
            NoPlantsFoundException, IllegalArgumentException{
        return plantService.getPlantsByCategory(plantCategory);
    }

    @PutMapping(path = "/plant/{plantId}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePlantAmount(
            @PathVariable("plantId") Long plantId,
            @RequestParam(required = true) Integer newAmount) throws
            PlantIdNotFoundException, IllegalArgumentException{
        plantService.updatePlantAmount(plantId, newAmount);
    }

    @DeleteMapping(path = "/plant")
    @ResponseStatus(HttpStatus.OK)
    public void deletePlant(@RequestBody Long plantId) throws PlantIdNotFoundException{
        plantService.deletePlant(plantId);
    }
}
