public class EmptySelectorException extends EmptyFieldException {
    public EmptySelectorException(String operation) {
        super("Please select a task to " + operation);
    }
}
