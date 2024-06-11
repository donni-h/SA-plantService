package de.htw.saplantservice.port.user.exception;

import de.htw.saplantservice.core.domain.model.Category;

public class NoPlantsFoundException extends RuntimeException{
    public NoPlantsFoundException(){
        super("No plants were found.");
    }
    public NoPlantsFoundException(Category plantCategory){
        super("No plants in the category: " + plantCategory + " were found.");
    }
    public NoPlantsFoundException(String plantName){
        super("No plants with the name: " + plantName + " were found.");
    }
}
