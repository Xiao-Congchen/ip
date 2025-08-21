public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String desc, String from, String to){
        super(desc);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + String.format(" (From: %s To: %s)", this.from, this.to);
    }
}
