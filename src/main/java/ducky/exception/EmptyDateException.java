package ducky.exception;

public class EmptyDateException extends EmptyException {
    public EmptyDateException(String fieldName) {
        super("No " + fieldName + "? Welp guess I'll just float around until you tell me :/");
    }
}
