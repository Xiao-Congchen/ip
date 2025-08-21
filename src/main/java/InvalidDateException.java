public class InvalidDateException extends InvalidException {
    public InvalidDateException(String fieldName) {
        super("That's an invalid " + fieldName + " date/time! Quack twice and try again~");
    }
}
