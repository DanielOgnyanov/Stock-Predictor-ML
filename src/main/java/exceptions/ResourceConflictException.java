package main.java.exceptions;

public class ResourceConflictException extends ServiceException{

    public ResourceConflictException(String message) {
        super(message, 409);
    }
}
