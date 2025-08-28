package ducky.command;

import ducky.ui.Ui;

import ducky.datahandling.Storage;
import ducky.datahandling.TaskList;

public class ListCmd extends Command {
    public ListCmd () {
        super();
    }
    public boolean isBye() {
        return false;
    }

    @Override
    public String execute(Ui ui, Storage storage, TaskList taskList) {
        return taskList.list();
    }
}
