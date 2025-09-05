package ducky;

import ducky.command.Command;
import ducky.datahandling.TaskList;
import ducky.exception.DuckyExceptions;
import ducky.stringprocessing.Parser;
import ducky.datahandling.Storage;
import ducky.ui.Ui;

import java.io.File;
import java.util.Scanner;

/**
 * The Ducky chatbot is a task tracker that helps
 * users manage their tasks through text commands.
 *
 * It supports commands for adding, listing, marking, unmarking,
 * and deleting tasks, as well as saving and loading tasks from storage
 * between sessions.
 * Tasks are stored as specific types, namely ToDo, Deadline, and Event.
 */
public class Ducky {
    Ui ui;
    Storage storage;
    TaskList taskList;

    public Ducky() {
        ui = new Ui();
        storage = new Storage(String.format("data%stasks.txt", File.separator));
        taskList = new TaskList(storage.read(), storage, ui);  // Load in existing tasks, if any
    }

    public static void main(String[] args) {
        Ducky ducky = new Ducky();

        String addOn = "";
        if (!ducky.taskList.isEmpty()) {
            addOn = "\n\n\tOoo... I already see some of your tasks on my shelf!" +
                    "\n\tI can bring them to you with \"list\"!";
        }
        ducky.ui.hello(addOn);

        Scanner scanner = new Scanner(System.in);
        boolean isBye = false;
        while (!isBye) {
            try {
                String command = scanner.nextLine().trim();
                Command c = Parser.parse(command, ducky.taskList.size());
                c.execute(ducky.ui, ducky.storage, ducky.taskList);
                isBye = c.isBye();
            } catch (DuckyExceptions e) {
                ducky.ui.speak(e.getMessage());
            }
        }
    }

    /**
     * A method to run the Ducky program with simulated terminal input.
     *
     * @param input Ordinary terminal user input.
     * @return Content string of Ducky's response, without paddings or indentations.
     */
    public String simulator(String input) {
        try {
            Command c = Parser.parse(input, taskList.size());
            return c.execute(ui, storage, taskList);
        } catch (DuckyExceptions e) {
            return e.getMessage();
        }
    }

    public String welcome() {
        String addOn = "";
        if (!taskList.isEmpty()) {
            addOn = "\n\nOoo... I already see some of your tasks on my shelf!" +
                    "\nI can bring them to you with \"list\"!";
        }
        return ui.hello(addOn);
    }
}
