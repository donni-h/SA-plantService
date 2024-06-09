package de.htw.saplantservice.exception;

import de.htw.saplantservice.core.domain.model.Category;

public class PlantsNotFoundInCategoryException extends RuntimeException{
    public PlantsNotFoundInCategoryException(Category plantCategory){
        super("No plants in the category: " + plantCategory + " were found.");
    }
}
