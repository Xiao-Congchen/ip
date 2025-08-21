import java.util.Scanner;
import java.util.ArrayList;

public class Ducky {
    private static final String DIVLINE = "\t------------------------------";
    private static ArrayList<Task> memory = new ArrayList<Task>();

    public static void main(String[] args) {
        greet();
        echo();
    }

    private static void speak(String msg) {
        System.out.println(DIVLINE);
        System.out.println("\t" + msg + "\n\tQuack!");
        System.out.println(DIVLINE);
    }

    private static void greet() {
        speak("Hi I'm Ducky!\n\tHow can I help you?");
    }

    private static void exit() {
        speak("Bye! See you soon!");
    }

    private static void list() {
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < memory.size(); i++) {
            content.append(String.format("\t%d. %s\n", i + 1, memory.get(i)));
        }
        speak(String.valueOf(content).substring(1));
    }

    private static void echo() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("bye")) {
                exit();
                break;
            } else if (command.equalsIgnoreCase("list")) {
                list();
            } else if (command.startsWith("mark")) {
                int taskId = Integer.parseInt(command.substring(5));
                memory.get(taskId - 1).changeStat();
                speak(String.format("Sure! I've marked this task as done!\n\t%s", memory.get(taskId - 1)));
            } else if (command.startsWith("unmark")) {
                int taskId = Integer.parseInt(command.substring(7));
                memory.get(taskId - 1).changeStat();
                speak(String.format("Sure! I've marked this task as not done!\n\t%s", memory.get(taskId - 1)));
            } else {
                speak("Remembering: " + command);
                memory.add(new Task(command));
            }
        }
    }
}
