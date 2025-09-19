package ducky.exception;

public class InvalidSelectorException extends InvalidException {
    public InvalidSelectorException() {
        super("Hmm, I can't find that task. Did you drop it in the pond?");
    }
}
