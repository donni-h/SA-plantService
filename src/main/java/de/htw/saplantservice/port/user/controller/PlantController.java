package de.htw.saplantservice.port.user.controller;

import de.htw.saplantservice.core.domain.model.Category;
import de.htw.saplantservice.core.domain.model.Plant;
import de.htw.saplantservice.core.domain.service.interfaces.IPlantService;
import de.htw.saplantservice.port.user.exception.PlantIdNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Validated
public class PlantController {
    private final IPlantService plantService;
    @Autowired
    public PlantController(IPlantService plantService){
        this.plantService = plantService;
    }

    @PostMapping(path = "/plant")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Plant createPlant(@Valid @RequestBody Plant plant){
        if (plant.getPlantId()!=null) throw new IllegalArgumentException("Plant ID is created automatically and " +
                "should not be given.");
        return plantService.createPlant(plant);
    }

    @GetMapping(path = "/plant/{plantId}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Plant getPlantById(
            @PathVariable("plantId")
            @NotNull(message = "PlantId cannot be null")
            UUID plantId) throws PlantIdNotFoundException {
        return plantService.getPlantById(plantId);
    }

    @GetMapping(path = "/plants")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Plant> getAllPlants(){
        return plantService.getAllPlants();
    }

    @GetMapping(path = "/plants/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Plant> getPlantsByName(
            @PathVariable("name")
            @NotNull(message = "Name cannot be null")
            @NotBlank(message = "Name cannot be empty")
            String plantName){
        return plantService.getPlantsByName(plantName);
    }

    @GetMapping(path = "/plants/category/{category}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Plant> getPlantsByCategory(
            @PathVariable("category")
            @NotNull(message = "Category cannot be null")
            Category plantCategory){
        return plantService.getPlantsByCategory(plantCategory);
    }

    @PutMapping(path = "/plant/{plantId}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Plant updatePlantAmount(
            @PathVariable("plantId")
            @NotNull(message = "PlantId cannot be null")
            UUID plantId,
            @RequestParam(required = true)
            @PositiveOrZero(message = "newAmount must be positive or zero")
            @NotNull(message = "newAmount cannot be null")
            Integer newAmount) throws PlantIdNotFoundException{
        return plantService.updatePlantAmount(plantId, newAmount);
    }

    @DeleteMapping(path = "/plant/{plantId}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePlant(
            @PathVariable("plantId")
            @NotNull(message = "PlantId cannot be null")
            UUID plantId) throws PlantIdNotFoundException{
        plantService.deletePlant(plantId);
    }

    @DeleteMapping(path = "/plants")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllPlants(){
        plantService.deleteAllPlants();
    }
}
