import java.util.Scanner;
import java.util.ArrayList;

public class Ducky {
    private static final String DIVLINE = "\t------------------------------";
    private static ArrayList<String> memory = new ArrayList<String>();

    public static void main(String[] args) {
        greet();
        echo();
    }

    public static void speak(String msg) {
        System.out.println(DIVLINE);
        System.out.println("\t" + msg + "\n\tQuack!");
        System.out.println(DIVLINE);
    }

    public static void greet() {
        speak("Hi I'm Ducky!\n\tHow can I help you?");
    }

    public static void exit() {
        speak("Bye! See you soon!");
    }

    public static void list() {
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < memory.size(); i++) {
            content.append(String.format("\t%d. %s\n", i + 1, memory.get(i)));
        }
        speak(String.valueOf(content).substring(1));
    }

    public static void echo() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("bye")) {
                exit();
                break;
            } else if (command.equalsIgnoreCase("list")) {
                list();
            } else {
                speak("Remembering: " + command);
                memory.add(command);
            }
        }
    }
}
