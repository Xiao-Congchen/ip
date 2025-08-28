/**
 * The Ducky chatbot is a task tracker that helps
 * users manage their tasks through text commands.
 *
 * It supports commands for adding, listing, marking, unmarking,
 * and deleting tasks, as well as saving and loading tasks from storage
 * between sessions.
 * Tasks are stored as specific types, namely ToDo, Deadline, and Event.
 */

import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class Ducky {
    private static final String DIVLINE = "\t-------------------------------------";
    private static ArrayList<Task> memory;
    private static final Storage storage = new Storage(String.format("data%stasks.txt", File.separator));

    public enum CommandTypes {
        TODO, DEADLINE, EVENT, LIST, MARK, UNMARK, DELETE
    }

    public static void main(String[] args) {
        speak("Hi I'm Ducky!\n\tHow can I help you?");
        start();
    }

    private static void start() {
        memory = storage.read();  // Load in existing tasks, if any
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

    private static void executeCommand(String[] keywordAndRest) throws DuckyExceptions {
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

        ArrayList<String> vars;

        switch(type) {
        case TODO:
            vars = Parser.parse("T", rest);
            addTask(new ToDo(vars.get(0), false));
            break;

        case DEADLINE:
            vars = Parser.parse("D", rest);
            addTask(new Deadline(vars.get(0), false, vars.get(1)));
            break;

        case EVENT:
            vars = Parser.parse("E", rest);
            addTask(new Event(vars.get(0), false, vars.get(1), vars.get(2)));
            break;

        case LIST:
            if (memory.isEmpty()) {
                speak("Pond's all clear - no tasks yet!");
            } else {
                list();
            }
            break;

        case MARK:
            toggleMark(validateSelector(rest, "mark"), true);
            break;

        case UNMARK:
            toggleMark(validateSelector(rest, "unmark"), false);
            break;

        case DELETE:
            int taskId = validateSelector(rest, "mark");
            Task temp = memory.get(taskId - 1);
            memory.remove(taskId - 1);
            speak(String.format("Noms! I've gobbled up:\n\t\t%s\n\tNow you have a total of %d tasks!",
                    temp, memory.size()));
            break;

        default:
            throw new InvalidCommandException();
        }
    }

    private static void speak(String msg) {
        System.out.println(DIVLINE);
        System.out.println("\t" + msg);
        System.out.println(DIVLINE);
    }

    private static void bye() {
        speak("Bye bye! See you soon!");
        if (!storage.save(memory)) {
            speak("Your tasks have been lost to the pond... Quack...");
        };
    }

    private static void list() {
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < memory.size(); i++) {
            content.append(String.format("\t%d. %s\n", i + 1, memory.get(i)));
        }
        speak("Here is your Task List! Quackk\n\t" + content.toString().trim());
    }

    private static void addTask(Task newTask) {
        memory.add(newTask);
        String addOn = "";
        if (!storage.save(memory)) {
           addOn = "\nBut I couldn't send this task to the clouds... Quack...";
        };
        speak(String.format("Gotcha! I've added:\n\t\t%s\n\tNow you have a total of %d tasks.%s",
                memory.get(memory.size()-1), memory.size(), addOn));
    }

    private static void toggleMark(int taskId, Boolean markState) {
        memory.get(taskId - 1).setStat(markState);
        speak(String.format("Quack! I've marked this task as %s!\n\t%s",
                markState ? "done" : "not done", memory.get(taskId - 1)));
    }

    private static int validateSelector(String num, String selector) throws DuckyExceptions{
        if (num.isEmpty()) throw new EmptySelectorException(selector);
        try {
            int taskId = Integer.parseInt(num);
            if (taskId < 1 || taskId > memory.size()) throw new InvalidSelectorException();
            return taskId;
        } catch (NumberFormatException e) {
            throw new InvalidSelectorException();
        }
    }
}
