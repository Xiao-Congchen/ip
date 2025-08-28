package ducky.command;

import ducky.ui.Ui;

import ducky.datahandling.Storage;
import ducky.datahandling.TaskList;

import java.util.ArrayList;

public class AddCmd extends Command {
    private final String type;
    private final ArrayList<Object> vars;

    public AddCmd(String type, ArrayList<Object> vars) {
        super();
        this.type = type;
        this.vars = vars;
    }

    public boolean isBye() {
        return false;
    }

    @Override
    public String execute(Ui ui, Storage storage, TaskList taskList) {
        return taskList.addTask(type, vars);
    }
}
