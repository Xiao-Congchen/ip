/**
 * Represents an abstract task with a description and a marked status.
 *
 * It serves as the base for different types of tasks, such as ToDo, Deadline, and Event.
 * It provides common fields and methods for handling task state,
 * as well as a factory method to reconstruct tasks from stored data.
 */
public abstract class Task {
    protected String desc;
    protected boolean isDone;

    public Task(String desc) {
        this.desc = desc;
        this.isDone = false;
    }

    private String getStat() {
        return (isDone ? "X" : " ");
    }

    public void setStat(Boolean status) {
        this.isDone = status;
    }

    /**
     * Returns an appropriate Task object using input from the stored file
     * @param variables A string array of task type, marked indicator and any other fields
     * @return Task object
     */
    public static Task createAppropriateTask(String[] variables) {
        String taskType = variables[0];
        boolean isDone = variables[1].equals("1");
        String desc = variables[2];

        switch (taskType){
        case "T":
            return new ToDo(desc, isDone);
        case "D":
            return new Deadline(desc, isDone, variables[3]);
        case "E":
            return new Event(desc, isDone, variables[3], variables[4]);
        }
        return null;
    }

    /**
     * Returns a readable, easy-to-parse format for storage use
     * @return String representation of task
     */
    public abstract String getStoreFormat();

   @Override
    public String toString() {
        return String.format("[%s] %s", getStat(), this.desc);
   }
}
