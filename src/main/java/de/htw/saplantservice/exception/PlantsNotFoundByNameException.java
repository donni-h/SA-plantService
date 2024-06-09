package de.htw.saplantservice.exception;

public class PlantsNotFoundByNameException extends RuntimeException{
    public PlantsNotFoundByNameException(String plantName){
        super("No plants with the name: " + plantName + " were found.");
    }
}
