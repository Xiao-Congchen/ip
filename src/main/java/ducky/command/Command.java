package ducky.command;

import ducky.ui.Ui;

import ducky.datahandling.Storage;
import ducky.datahandling.TaskList;

public abstract class Command {
    public Command() {
    }

    /**
     * Tells the program when to terminate.
     * @return true only when bye() is called.
     */
    public abstract boolean isBye();

    /**
     * Executes the appropriate command.
     * @param ui The ui for displaying responses.
     * @param storage The storage for storing records into local txt file.
     * @param taskList The task list to update and view tasks.
     * @return The response passed to the ui.
     */
    public abstract String execute(Ui ui, Storage storage, TaskList taskList);
}
