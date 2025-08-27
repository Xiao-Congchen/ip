public class ToDo extends Task {


    public ToDo(String desc, boolean state) {
        super(desc);
        this.isDone = state;
    }

    /**
     * Returns a readable, easy-to-parse format for storage use
     * @return String representation of task
     */
    public String getStoreFormat() {
        // 1 means done(marked), 0 means not done(unmarked)
        return String.format("T | %d | %s", isDone ? 1 : 0, desc);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
