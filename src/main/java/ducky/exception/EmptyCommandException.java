package ducky.exception;

public class EmptyCommandException extends EmptyException {
    public EmptyCommandException() {
        super("""
                Quack quack? Let me know what you'd like to do!
                \t\
                You can try:
                \ttodo, deadline, event, list, mark, unmark, delete, bye""");
    }
}
