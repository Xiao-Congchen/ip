package ducky.command;

import ducky.datahandling.Storage;
import ducky.datahandling.TaskList;
import ducky.task.Task;
import ducky.ui.Ui;

import java.util.ArrayList;

public class FindCmd extends Command {
    private String findStr;

    public FindCmd(String findStr) {
        this.findStr = findStr;
    }

    @Override
    public boolean isBye() {
        return false;
    }

    @Override
    public String execute(Ui ui, Storage storage, TaskList taskList) {
        ArrayList<Task> filteredTasks = new ArrayList<Task>();
        for (Task task : taskList.getAll()) {
            if (task.getDesc().contains(findStr)) {
                filteredTasks.add(task);
            }
        }
        if (filteredTasks.isEmpty()) {
            String msg = "I do not see any matching tasks floating around.\n\tTry another keyword!";
            ui.speak(msg);
            return msg;
        } else {
            TaskList filtered = new TaskList(filteredTasks, null, ui);
            return filtered.list("Quack! You are in luck!\n\tI found some matching tasks\n\t");
        }
    }
}
