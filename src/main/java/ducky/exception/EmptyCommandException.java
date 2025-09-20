package ducky.exception;

public class EmptyCommandException extends EmptyException {
    public EmptyCommandException() {
        super("""
                Quack quack? What shall we do today?
                \t\
                Try one of these:
                \ttodo, deadline, event, list, mark, unmark, delete, bye.
                """);
    }
}
