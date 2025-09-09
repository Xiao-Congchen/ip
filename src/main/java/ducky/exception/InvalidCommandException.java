package ducky.exception;

public class InvalidCommandException extends DuckyException {
    public InvalidCommandException() {
        super("""
                Unknown command. My duck brain can’t process that...
                \t\
                You can try:
                \ttodo, deadline, event, list, mark, unmark, delete, clearall, bye""");
    }
}
