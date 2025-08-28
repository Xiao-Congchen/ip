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

public class Ducky {
    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage(String.format("data%stasks.txt", File.separator));
        TaskList taskList = new TaskList(storage.read(), storage, ui);  // Load in existing tasks, if any

        String addOn = "";
        if (!taskList.isEmpty()) {
            addOn = "\n\n\tOoo... I already see some of your tasks on my shelf!" +
                    "\n\tI can bring them to you with \"list\"!";
        }
        ui.hello(addOn);

        Scanner scanner = new Scanner(System.in);
        boolean isBye = false;
        while (!isBye) {
            try {
                String command = scanner.nextLine().trim();
                Command c = Parser.parse(command, taskList.size());
                c.execute(ui, storage, taskList);
                isBye = c.isBye();
            } catch (DuckyExceptions e) {
                ui.speak(e.getMessage());
            }
        }
    }
}
