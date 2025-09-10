package ducky.exception;

public class ConflictingDateException extends RuntimeException {
    public ConflictingDateException(String taskType, String conflictingEvent) {
        super("QUACK! The duration of your new " + taskType
                + "\nClashes with " + conflictingEvent + "!"
                + "\n Don't over-commit!");
    }
}
