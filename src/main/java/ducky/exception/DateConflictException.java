package ducky.exception;

public class DateConflictException extends DuckyException {
    public DateConflictException(String taskType, String conflictingEvent) {
        super("QUACK! The duration of your new " + taskType
                + "\nClashes with " + conflictingEvent + "!"
                + "\n Don't over-commit!");
    }
}
