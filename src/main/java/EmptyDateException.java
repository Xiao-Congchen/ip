public class EmptyDateException extends EmptyException {
    public EmptyDateException(String fieldName) {
        super("No " + fieldName + "? Guess I’ll just float around until you tell me :/");
    }
}
