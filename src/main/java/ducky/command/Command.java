package ducky.command;

import ducky.ui.Ui;

import ducky.datahandling.Storage;
import ducky.datahandling.TaskList;

public abstract class Command {
    public Command() {
    }

    public abstract boolean isBye();

    public abstract String execute(Ui ui, Storage storage, TaskList taskList);
}
