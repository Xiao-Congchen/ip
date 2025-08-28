import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a specific type of Task that has a
 * "/by" variable to store a task completion deadline.
 */

public class Deadline extends Task {
    protected LocalDateTime by;

    public Deadline(String desc, boolean marked, LocalDateTime by) {
        super(desc);
        this.isDone = marked;
        this.by = by;
    }

    public String getStoreFormat() {
        // 1 means done(marked), 0 means not done(unmarked)
        return String.format("T | %d | %s | %s", isDone ? 1 : 0, desc, by);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + String.format(" (by: %s)", StringifyDate.friendlyDate(this.by));
    }
}
