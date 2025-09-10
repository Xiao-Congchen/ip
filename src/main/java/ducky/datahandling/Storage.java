package ducky.datahandling;

import ducky.exception.DuckyException;
import ducky.task.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a container for the stored data.
 *
 * It provides saving and reading functionalities to a
 * file specified by a path in the instance.
 */
public class Storage {
    private final String path;

    public Storage(String path) {
        this.path = path;
    }

    public boolean write(ArrayList<Task> tasks) {
        try {
            FileWriter writer = new FileWriter(String.valueOf(path));
            for (Task task : tasks) {
                // Each task will be separated by a newline char
                writer.write(task.getStoreFormat() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            // Failed save from path error
            System.out.println(e);
            System.out.println("Path error");
            return false;
        }
        return true;
    }

    public ArrayList<Task> read() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        try {
            Scanner scanner = new Scanner(new File(path));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] variables = line.split(" \\| ");
                tasks.add(Task.createAppropriateTask(variables));
            }
            scanner.close();
        } catch (FileNotFoundException ignored) {
            // If the file does not exist, it will be created after one iteration
        } catch (DuckyException d) {
            System.out.println("Error with reading stored tasks: " + d.getMessage());
        }
        return tasks;
    }
}
