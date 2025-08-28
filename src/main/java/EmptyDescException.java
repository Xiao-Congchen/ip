public class EmptyDescException extends EmptyException {
    public EmptyDescException(String taskType) {
        super(String.format("Quack! You forgot to tell me what the %s is about. I can’t swim without water!", taskType));
    }
}
