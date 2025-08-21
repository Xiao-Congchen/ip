public class ToDo extends Task {
    public ToDo(String desc) throws EmptyDescException {
        super(parseDesc(desc));
    }

    private static String parseDesc(String desc) throws EmptyDescException {
        if (desc == null || desc.trim().isEmpty()) throw new EmptyDescException();
        return desc.trim();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
