package ducky.datahandling;

import ducky.task.Deadline;
import ducky.task.Event;
import ducky.task.Task;
import ducky.task.ToDo;
import ducky.ui.Ui;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> memory;
    private final Storage storage;
    private final Ui ui;

    public TaskList(ArrayList<Task> tasks, Storage storage, Ui ui) {
        this.memory = tasks;
        this.storage = storage;
        this.ui = ui;
    }

    public String addTask(String type, ArrayList<Object> vars) {
        String msg = "";
        switch(type) {
        case "T":
            msg = addTaskHelper(new ToDo((String)vars.get(0), false));
            break;
        case "D":
            msg = addTaskHelper(new Deadline((String)vars.get(0), false, (LocalDateTime)vars.get(1)));
            break;
        case "E":
            msg = addTaskHelper(new Event((String)vars.get(0), false,
                    (LocalDateTime)vars.get(1), (LocalDateTime)vars.get(2)));
            break;
        }
        return msg;
    }

    private String addTaskHelper(Task newTask) {
        memory.add(newTask);
        String addOn = "";
        if (!storage.save(memory)) {
            addOn = "\nBut I couldn't send this task to the clouds... Quack...";
        };
        String msg = String.format("Gotcha! I've added:\n\t\t%s\n\tNow you have a total of %d tasks.%s",
                memory.get(memory.size()-1), memory.size(), addOn);
        ui.speak(msg);
        storage.save(memory);
        return msg;
    }

    public String list() {
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < memory.size(); i++) {
            content.append(String.format("\t%d. %s\n", i + 1, memory.get(i)));
        }
        String msg = "Here is your Task List! Quackk\n\t" + content.toString().trim();
        ui.speak(msg);
        return msg;
    }

    public String toggleMark(int taskId, Boolean markState) {
        memory.get(taskId - 1).setStat(markState);
        String msg = String.format("Quack! I've marked this task as %s!\n\t%s",
                markState ? "done" : "not done", memory.get(taskId - 1));
        ui.speak(msg);
        return msg;
    }

    public String delete(int taskId) {
        Task temp = memory.get(taskId - 1);
        memory.remove(taskId - 1);
        String msg = String.format("Noms! I've gobbled up:\n\t\t%s\n\tNow you have a total of %d tasks!",
                temp, memory.size());
        ui.speak(msg);
        storage.save(memory);
        return msg;
    }

    public Task get(int index) {
        return memory.get(index);
    }

    public ArrayList<Task> getAll() {
        return memory;
    }

    public int size() {
        return memory.size();
    }

    public void remove(int index) {
        memory.remove(index);
    }

    public String clear() {
        memory.clear();
        String msg = "I've cleared all your tasks!\n\tGood job and keep on quacking!";
        ui.speak(msg);
        storage.save(memory);
        return msg;
    }

    public boolean isEmpty() {
        return memory.isEmpty();

    }

}
