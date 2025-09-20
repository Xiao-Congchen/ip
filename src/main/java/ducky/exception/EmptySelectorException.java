package ducky.exception;

public class EmptySelectorException extends EmptyException {
    public EmptySelectorException(String operation) {
        super("Quackkk~ I need to know which task to " + operation + "!");
    }
}
