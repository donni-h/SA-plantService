package de.htw.saplantservice.port.user.exception;

public class PlantIdAlreadyExistsException extends IllegalArgumentException{
    public PlantIdAlreadyExistsException (Long id){
        super("Plant with id: " + id + " already exists.");
    }
}
