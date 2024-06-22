package de.htw.saplantservice.port.controller;

import de.htw.saplantservice.core.domain.model.Category;
import de.htw.saplantservice.core.domain.model.Plant;
import de.htw.saplantservice.core.domain.service.interfaces.IPlantService;
import de.htw.saplantservice.port.user.exception.PlantIdAlreadyExistsException;
import de.htw.saplantservice.port.user.exception.PlantIdNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class PlantController {
    private final IPlantService plantService;
    @Autowired
    public PlantController(IPlantService plantService){
        this.plantService = plantService;
    }

    @PostMapping(path = "/plant")
    @ResponseStatus(HttpStatus.OK)
    public void createPlant(@Valid @RequestBody Plant plant) throws IllegalArgumentException,
            PlantIdAlreadyExistsException {
        if (plant == null) throw new IllegalArgumentException("Plant cannot be null.");
        plantService.createPlant(plant);
    }

    @GetMapping(path = "/plant/{plantId}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Plant getPlantById(@PathVariable("plantId") Long plantId) throws IllegalArgumentException,
            PlantIdNotFoundException {
        if(plantId == null) throw new IllegalArgumentException("plantId cannot be null");
        return plantService.getPlantById(plantId);
    }

    @GetMapping(path = "/plants")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Plant> getAllPlants(){
        return plantService.getAllPlants();
    }

    @GetMapping(path = "/plants/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Plant> getPlantsByName(@PathVariable("name") String plantName) throws
            IllegalArgumentException{
        if (plantName == null) throw new IllegalArgumentException("Plant name cannot be null.");
        if (plantName.isEmpty()) throw new IllegalArgumentException("Plant name cannot be empty.");
        return plantService.getPlantsByname(plantName);
    }

    @GetMapping(path = "/plants/category/{category}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Plant> getPlantsByCategory(@PathVariable("category") Category plantCategory) throws
            IllegalArgumentException{
        if (plantCategory == null) throw new IllegalArgumentException("Category cannot be null.");
        return plantService.getPlantsByCategory(plantCategory);
    }

    @PutMapping(path = "/plant/{plantId}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePlantAmount(
            @PathVariable("plantId") Long plantId,
            @RequestParam(required = true) Integer newAmount) throws
            PlantIdNotFoundException, IllegalArgumentException{
        if(plantId == null) throw new IllegalArgumentException("PlantId cannot be null.");
        if(newAmount == null) throw new IllegalArgumentException("new Amount cannot be null.");
        plantService.updatePlantAmount(plantId, newAmount);
    }

    @DeleteMapping(path = "/plant")
    @ResponseStatus(HttpStatus.OK)
    public void deletePlant(@RequestBody Long plantId) throws PlantIdNotFoundException{
        plantService.deletePlant(plantId);
    }
}
