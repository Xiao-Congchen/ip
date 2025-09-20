package ducky.exception;

public class EmptyDescException extends EmptyException {
    public EmptyDescException(String taskType) {
        super(String.format("Quack! You forgot to tell me what the %s is about.\nI can't swim without water!", taskType));
    }
}
