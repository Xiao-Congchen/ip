import java.util.Scanner;
import java.util.ArrayList;

public class Ducky {
    private static final String DIVLINE = "\t-------------------------------------";
    private static final ArrayList<Task> memory = new ArrayList<Task>();

    public enum CommandTypes {
        TODO, DEADLINE, EVENT, LIST, MARK, UNMARK, DELETE
    }

    public static void main(String[] args) {
        greet();
        echo();
    }

    private static void speak(String msg) {
        System.out.println(DIVLINE);
        System.out.println("\t" + msg);
        System.out.println(DIVLINE);
    }

    private static void greet() {
        speak("Hi I'm Ducky!\n\tHow can I help you?");
    }

    private static void echo() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String command = scanner.nextLine().trim();
                if (command.isEmpty()) throw new EmptyCommandException();
                String[] keywordAndRest = command.split(" ", 2);
                if (keywordAndRest.length == 1 && keywordAndRest[0].equalsIgnoreCase("bye")) {
                    bye();
                    break;
                } else {
                    executeCommand(keywordAndRest);
                }
            } catch (DuckyExceptions e) {
                speak(e.getMessage());
            }
        }
    }

    private static void bye() {
        speak("Bye bye! See you soon!");
    }

    private static void list() {
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < memory.size(); i++) {
            content.append(String.format("\t%d. %s\n", i + 1, memory.get(i)));
        }
        speak("Here is your Task List! Quackk\n\t" + content.toString().trim());
    }

    public static void addTask(Task newTask) {
        memory.add(newTask);
        speak(String.format("Gotcha! I've added:\n\t\t%s\n\tNow you have a total of %d tasks.",
                memory.get(memory.size()-1), memory.size()));
    }

    public static void toggleMark(int taskId, Boolean state) {
        memory.get(taskId - 1).setStat(state);
        speak(String.format("Quack! I've marked this task as %s!\n\t%s",
                state ? "done" : "not done", memory.get(taskId - 1)));
    }

    public static int checkValidSelector(String num, String selector) throws DuckyExceptions{
        if (num.isEmpty()) throw new EmptySelectorException(selector);
        try {
            int taskId = Integer.parseInt(num);
            if (taskId < 1 || taskId > memory.size()) throw new InvalidSelectorException();
            return taskId;
        } catch (NumberFormatException e) {
            throw new InvalidSelectorException();
        }
    }

    public static void executeCommand(String[] keywordAndRest) throws DuckyExceptions {
        String cmdType = keywordAndRest[0].toUpperCase();
        String rest;
        if (keywordAndRest.length == 2) {
            rest = keywordAndRest[1];
        } else {
            rest = "";
        }

        CommandTypes type;
        try {
            type = CommandTypes.valueOf(cmdType);
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException();
        }

        switch(type) {
            case TODO:
                addTask(new ToDo(rest));
                break;

            case DEADLINE:
                addTask(new Deadline(rest));
                break;

            case EVENT:
                addTask(new Event(rest));
                break;

            case LIST:
                if (memory.isEmpty()) {
                    speak("Pond's all clear - no tasks yet!");
                } else {
                    list();
                }
                break;

            case MARK:
                toggleMark(checkValidSelector(rest, "mark"), true);
                break;

            case UNMARK:
                toggleMark(checkValidSelector(rest, "unmark"), false);
                break;

            case DELETE:
                int taskId = checkValidSelector(rest, "mark");
                Task temp = memory.get(taskId - 1);
                memory.remove(taskId - 1);
                speak(String.format("Noms! I've gobbled up:\n\t\t%s\n\tNow you have a total of %d tasks!",
                        temp, memory.size()));
                break;

            default:
                throw new InvalidCommandException();
        }
    }
}
