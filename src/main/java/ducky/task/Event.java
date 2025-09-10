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

    /**
     * Used to find out if a given dateTime lies within the range of the Event
     * @param start Some given start dateTime
     * @param end Some given end dateTime
     * @return whether there is a conflict
     */
    public boolean isConflict(LocalDateTime start, LocalDateTime end) {
        // As long as "start" is after "to" or "end" is before "from", no conflict
        return !(start.isAfter(to) || end.isBefore(from));
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + String.format(" (From: %s To: %s)",
                DateProcessor.friendlyDate(this.from), DateProcessor.friendlyDate(this.to));
    }
}
