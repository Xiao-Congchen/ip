package ducky.exception;

public class DateConflictException extends DuckyException {
    public DateConflictException(String taskType, int conflictingEvent) {
        super("QUACK! The duration of your new " + taskType
                + "\nClashes with " + taskType + " at position " + conflictingEvent + "!"
                + "\nDon't over-commit!");
    }
}
