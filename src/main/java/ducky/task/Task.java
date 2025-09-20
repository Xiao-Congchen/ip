package ducky.task;

import ducky.inputprocessing.Parser;
import ducky.exception.DuckyException;

/**
 * Represents an abstract task with a description and a marked status.
 *
 * It serves as the base for different types of tasks, such as ducky.task.ToDo, ducky.task.Deadline, and ducky.task.Event.
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

    protected String getDoneStatus() {
        return (isDone ? "X" : "⠀");
    }

    public void setDoneStatus(Boolean status) {
        this.isDone = status;
    }

    public String getDesc() {
        return this.desc;
    }

    /**
     * Returns an appropriate ducky.task.Task object using input from the stored file
     * @param variables A string array of task type, marked indicator and any other fields
     * @return ducky.task.Task object
     */
    public static Task createAppropriateTask(String[] variables) throws DuckyException {
        String taskType = variables[0];
        boolean isDone = variables[1].equals("1");
        String desc = variables[2];

        switch (taskType){
        case "T":
            return new ToDo(desc, isDone);
        case "D":
            return new Deadline(desc, isDone, Parser.parseDate(variables[3], "'/by'"));
        case "E":
            return new Event(desc, isDone,
                    Parser.parseDate(variables[3], "'/from'"),
                    Parser.parseDate(variables[4], "'/to'"));
        }
        return null;
    }

    /**
     * Formats the task details into a form suited for storing in a local txt file.
     * @return Formatted task string.
     */
    public abstract String getStoreFormat();

   @Override
    public String toString() {
        return this.desc;
   }
}
