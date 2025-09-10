package ducky.task;

import ducky.inputprocessing.DateProcessor;
import java.time.LocalDateTime;

/**
 * Represents a specific type of ducky.task.Task that has a
 * "/from" variable and "/to" variable to store the start
 * and end times of an event.
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    // Constructor for reading from file on program start
    public Event(String desc, boolean marked, LocalDateTime from, LocalDateTime to) {
        super(desc);
        this.isDone = marked;
        this.from = from;
        this.to = to;
    }

    public String getStoreFormat() {
        // 1 means done(marked), 0 means not done(unmarked)
        return String.format("T | %d | %s | %s | %s", isDone ? 1 : 0, desc, from, to);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + String.format(" (From: %s To: %s)",
                DateProcessor.friendlyDate(this.from), DateProcessor.friendlyDate(this.to));
    }
}
