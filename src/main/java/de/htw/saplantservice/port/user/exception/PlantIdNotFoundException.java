package de.htw.saplantservice.port.user.exception;

import java.util.UUID;

public class PlantIdNotFoundException extends RuntimeException{
    public PlantIdNotFoundException(UUID plantId){
        super("Plant with id: " + plantId + " could not be found.");
    }
}
