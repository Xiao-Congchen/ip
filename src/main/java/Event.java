public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String info) throws DuckyExceptions {
        super(parseDesc(info));
        this.from = parseFrom(info);
        this.to = parseTo(info);
    }

    private static String parseDesc(String info) throws EmptyDescException {
        if (info == null || info.trim().isEmpty()) throw new EmptyDescException();
        return info.split("/from", 2)[0].trim();
    }

    private static String parseFrom(String info) throws DuckyExceptions {
        if (!info.contains("/from")) throw new EmptyDateException("'/from'");
        String[] descFromTo = info.split("/from",2);
        if (descFromTo.length == 1 || descFromTo[1].trim().isEmpty()) {
            throw new InvalidDateException("'/from'");
        }
        return descFromTo[1].split("/")[0].trim();
    }

    private static String parseTo(String info) throws DuckyExceptions {
        if (!info.contains("/to")) throw new EmptyDateException("'/to'");
        String[] descFromTo = info.split("/to",2);
        if (descFromTo.length == 1 || descFromTo[1].trim().isEmpty()) {
            throw new InvalidDateException("'/to'");
        }
        return descFromTo[1].trim();
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + String.format(" (From: %s To: %s)", this.from, this.to);
    }
}
