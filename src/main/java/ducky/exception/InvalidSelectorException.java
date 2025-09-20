package ducky.exception;

public class InvalidSelectorException extends InvalidException {
    public InvalidSelectorException() {
        super("Quack? I can't find that task!\nDid it waddle away or sink in the pond? Let's try again!");
    }
}
