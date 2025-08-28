import java.time.LocalDateTime;
import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> memory;
    private final Storage storage;

    public TaskList(ArrayList<Task> tasks, Storage storage) {
        this.memory = tasks;
        this.storage = storage;
    }

    public void addTask(String type, String input) throws DuckyExceptions {
        ArrayList<Object> vars;
        vars = Parser.parse(type, input);
        switch(type) {
        case "TODO":
            addTaskHelper(new ToDo((String)vars.get(0), false));
            break;
        case "DEADLINE":
            addTaskHelper(new Deadline((String)vars.get(0), false, (LocalDateTime)vars.get(1)));
            break;
        case "EVENT":
            addTaskHelper(new Event((String)vars.get(0), false,
                    (LocalDateTime)vars.get(1), (LocalDateTime)vars.get(2)));
            break;
        }
    }

    private void addTaskHelper(Task newTask) {
        memory.add(newTask);
        String addOn = "";
        if (!storage.save(memory)) {
            addOn = "\nBut I couldn't send this task to the clouds... Quack...";
        };
        System.out.printf("Gotcha! I've added:\n\t\t%s\n\tNow you have a total of %d tasks.%s%n",
                memory.get(memory.size()-1), memory.size(), addOn);
//        speak(String.format("Gotcha! I've added:\n\t\t%s\n\tNow you have a total of %d tasks.%s",
//                memory.get(memory.size()-1), memory.size(), addOn));
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

    public void clear() {
        memory.clear();
    }

    public boolean isEmpty() {
        return memory.isEmpty();
    }

}
