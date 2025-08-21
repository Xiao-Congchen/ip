public class InvalidSelectorException extends InvalidFieldException {
    public InvalidSelectorException() {
        super("Please select a task within the list");
    }
}
