package ducky.task;

/**
 * Represents the most basic type of ducky.task.Task with no additional functionality
 */
public class ToDo extends Task {
    public ToDo(String desc, boolean state) {
        super(desc);
        this.isDone = state;
    }

    public String getStoreFormat() {
        // 1 means done(marked), 0 means not done(unmarked)
        return String.format("T | %d | %s", isDone ? 1 : 0, desc);
    }

    @Override
    public String toString() {
        return String.format("[%s]", getDoneStatus()) + "[T] " + super.toString();
    }
}
