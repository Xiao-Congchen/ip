package ducky.command;

import ducky.ui.Ui;

import ducky.datahandling.Storage;
import ducky.datahandling.TaskList;

public class ByeCmd extends Command {
    public boolean isBye() {
        return true;
    }

    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        ui.bye(storage, taskList);
    }
}
