package de.htw.saplantservice.exception;

public class PlantIdNotFoundException extends RuntimeException{
    public PlantIdNotFoundException(Long plantId){
        super("Plant with id: " + plantId + " could not be found.");
    }
}
