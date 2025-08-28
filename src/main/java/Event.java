/**
 * Represents a specific type of Task that has a
 * "/from" variable and "/to" variable to store the start
 * and end times of an event.
 */

import java.time.LocalDateTime;

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
                StringifyDate.friendlyDate(this.from), StringifyDate.friendlyDate(this.to));
    }
}
