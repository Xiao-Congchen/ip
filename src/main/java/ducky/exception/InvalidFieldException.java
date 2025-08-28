package ducky.exception;

public class InvalidFieldException extends InvalidException {
    public InvalidFieldException(String fieldName) {
        super("That's an invalid " + fieldName + " value! My duck brain can’t process that.");
    }
}
