package de.htw.saplantservice.port.controller;

import de.htw.saplantservice.core.domain.model.Category;
import de.htw.saplantservice.core.domain.model.Plant;
import de.htw.saplantservice.core.domain.service.interfaces.IPlantService;
import de.htw.saplantservice.port.user.exception.PlantIdAlreadyExistsException;
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
    public void createPlant(@Valid @RequestBody Plant plant){
        plantService.createPlant(plant);
    }

    @GetMapping(path = "/plant/{plantId}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Plant getPlantById(
            @PathVariable("plantId")
            @Positive(message = "PlantId must be positive")
            @NotNull(message = "PlantId cannot be null")
            Long plantId) throws PlantIdNotFoundException {
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
    public void updatePlantAmount(
            @PathVariable("plantId")
            @Positive(message = "PlantId must be positive")
            @NotNull(message = "PlantId cannot be null")
            Long plantId,
            @RequestParam(required = true)
            @PositiveOrZero(message = "newAmount must be positive or zero")
            @NotNull(message = "newAmount cannot be null")
            Integer newAmount) throws PlantIdNotFoundException{
        plantService.updatePlantAmount(plantId, newAmount);
    }

    @DeleteMapping(path = "/plant/{plantId}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePlant(
            @PathVariable("plantId")
            @Positive(message = "PlantId must be positive")
            @NotNull(message = "PlantId cannot be null")
            Long plantId) throws PlantIdNotFoundException{
        plantService.deletePlant(plantId);
    }
}
