package ducky.command;

import ducky.ui.Ui;

import ducky.datahandling.Storage;
import ducky.datahandling.TaskList;

public class DeleteCmd extends Command {
    private final int taskId;

    public DeleteCmd(int taskId) {
        this.taskId = taskId;
    }

    public boolean isBye() {
        return false;
    }

    @Override
    public String execute(Ui ui, Storage storage, TaskList taskList) {
        if (taskId > 0) {
            return taskList.delete(taskId);
        } else {
            return taskList.clear();
        }

    }
}
