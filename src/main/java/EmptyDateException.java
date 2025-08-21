public class EmptyDateException extends EmptyFieldException {
    public EmptyDateException(String fieldName) {
        super("Please include the " + fieldName + " date/time!");
    }
}
