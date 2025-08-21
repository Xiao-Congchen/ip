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
                if (memory.isEmpty()) {
                    speak("No tasks yet!");
                } else {
                    list();
                }

            } else if (command.startsWith("delete")) {
                    if (memory.isEmpty()) {
                        speak("No tasks to delete!");
                    } else {
                        try {
                            if (command.length() == 6) {
                                throw new EmptySelectorException("delete");
                            }

                            int taskId = Integer.parseInt(command.substring(7));
                            if (taskId > memory.size()) {
                                throw new InvalidSelectorException();
                            }
                            Task temp = memory.get(taskId - 1);
                            memory.remove(taskId - 1);
                            speak(String.format("Gotcha! I've deleted:\n\t\t%s\n\tNow you have a total of %d tasks.",
                                    temp, memory.size()));
                        } catch (NumberFormatException e) {
                            speak("Invalid task ID! Make sure you use a number!");
                        } catch (DuckyExceptions e) {
                            speak(e.getMessage());
                        }
                    }

                } else if (command.startsWith("mark")) {
                try {
                    if (command.length() == 4) {
                        throw new InvalidSelectorException();
                    }

                    int taskId = Integer.parseInt(command.substring(5));
                    if (taskId > memory.size()) {
                        throw new InvalidSelectorException();
                    }
                    memory.get(taskId - 1).changeStat();
                    speak(String.format("Sure! I've marked this task as done!\n\t%s", memory.get(taskId - 1)));

                } catch (NumberFormatException e) {
                    speak("Invalid task ID! Make sure you use a number!");
                } catch (InvalidSelectorException e) {
                    speak(e.getMessage());
                }

            } else if (command.startsWith("unmark")) {
                try {
                    if (command.length() == 6) {
                        throw new InvalidSelectorException();
                    }

                    int taskId = Integer.parseInt(command.substring(7));
                    if (taskId > memory.size()) {
                        throw new InvalidSelectorException();
                    }
                    memory.get(taskId - 1).changeStat();
                    speak(String.format("Sure! I've marked this task as not done!\n\t%s", memory.get(taskId - 1)));
                } catch (NumberFormatException e) {
                    speak("Invalid task ID! Make sure you use a number!");
                } catch (InvalidSelectorException e) {
                    speak(e.getMessage());
                }

            } else if (command.startsWith("todo")) {
                try {
                    if (command.length() == 4) {
                        throw new EmptyDescException();
                    }
                    String desc = command.substring(5);
                    if (desc.isBlank()) {
                        throw new EmptyDescException();
                    }
                    memory.add(new ToDo(desc));
                    speak(String.format("Gotcha! I've added:\n\t\t%s\n\tNow you have a total of %d tasks.",
                            memory.get(memory.size()-1), memory.size()));
                } catch (EmptyDescException e) {
                    speak(e.getMessage());
                }

            } else if (command.startsWith("deadline")) {
                try {
                    if (command.length() == 8) {
                        throw new EmptyDescException();
                    }
                    String[] data = command.substring(9).split("/by ");
                    if (data.length == 0) {
                        throw new EmptyDescException();
                    } else if (data.length == 1) {
                        throw new EmptyDateException("by");
                    }
                    memory.add(new Deadline(data[0], data[1]));
                    speak(String.format("Gotcha! I've added:\n\t\t%s\n\tNow you have a total of %d tasks.",
                            memory.get(memory.size()-1), memory.size()));

                } catch (DuckyExceptions e) {
                    speak(e.getMessage());
                }

            } else if (command.startsWith("event")) {
                try {
                    if (command.length() == 5) {
                        throw new EmptyDescException();
                    }
                    String[] desc = command.substring(6).split("/from ");
                    if (desc.length == 0) {
                        throw new EmptyDescException();
                    } else if (desc.length == 1) {
                        throw new EmptyDateException("from");
                    }

                    String[] times = desc[1].split(" /to ");
                    if (times.length == 1) {
                        throw new EmptyDateException("to");
                    }
                    memory.add(new Event(desc[0], times[0], times[1]));
                    speak(String.format("Gotcha! I've added:\n\t\t%s\n\tNow you have a total of %d tasks.",
                            memory.get(memory.size()-1), memory.size()));
                } catch (DuckyExceptions e) {
                    speak(e.getMessage());
                }

            } else {
                speak("Sorry, I do not understand your directions :(");
            }
        }
    }
}
