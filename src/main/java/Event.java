import java.util.ArrayList;

public class Event extends Task {
    protected String from;
    protected String to;

    // Constructor for reading from file on program start
    public Event(String desc, boolean marked, String from, String to) {
        super(desc);
        this.isDone = marked;
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a readable, easy-to-parse format for storage use
     * @return String representation of event
     */
    public String getStoreFormat() {
        // 1 means done(marked), 0 means not done(unmarked)
        return String.format("T | %d | %s | %s | %s", isDone ? 1 : 0, desc, from, to);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + String.format(" (From: %s To: %s)", this.from, this.to);
    }
}
