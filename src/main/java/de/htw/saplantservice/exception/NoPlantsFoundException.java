package de.htw.saplantservice.exception;

public class NoPlantsFoundException extends RuntimeException{
    public NoPlantsFoundException(){
        super("No plants were found.");
    }
}
