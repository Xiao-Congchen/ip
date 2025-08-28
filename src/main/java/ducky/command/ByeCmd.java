package ducky.command;

import ducky.ui.Ui;

import ducky.datahandling.Storage;
import ducky.datahandling.TaskList;

public class ByeCmd extends Command {
    public boolean isBye() {
        return true;
    }

    @Override
    public String execute(Ui ui, Storage storage, TaskList taskList) {
        return ui.bye(storage, taskList);
    }
}
