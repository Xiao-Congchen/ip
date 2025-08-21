public class Deadline extends Task {
    protected String by;

    public Deadline(String info) throws DuckyExceptions {
        super(parseDesc(info));
        this.by = parseBy(info);
    }

    private static String parseDesc(String info) throws EmptyDescException {
        if (info == null || info.trim().isEmpty()) throw new EmptyDescException();
        return info.split("/by", 2)[0].trim();
    }

    private static String parseBy(String info) throws DuckyExceptions {
        if (!info.contains("/by")) throw new EmptyDateException("'/by'");
        String[] descDate = info.split("/by",2);
        if (descDate.length == 1 || descDate[1].trim().isEmpty()) {
            throw new InvalidDateException("'/by'");
        }
        return descDate[1].trim();
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + String.format(" (by: %s)", this.by);
    }
}
