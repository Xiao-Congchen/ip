import java.util.Scanner;

public class Ducky {
    private static final String DIVLINE = "\t------------------------------";

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

    public static void echo() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("bye")) {
                exit();
                break;
            } else {
                speak(command);
            }
        }
    }
}
