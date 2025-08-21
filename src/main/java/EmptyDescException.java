public class EmptyDescException extends EmptyFieldException {
    public EmptyDescException() {
        super("Please include a task description!");
    }
}
