package exceptions;

public class ResourceConflictException extends ServiceException{

    public ResourceConflictException(String message) {
        super(message, 409);
    }
}
