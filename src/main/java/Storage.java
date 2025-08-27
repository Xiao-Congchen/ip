import java.io.FileWriter;

import java.io.IOException;
import java.util.ArrayList;

public class Storage {
    private final String path;

    public Storage(String path) {
        this.path = path;
    }

    public int save(ArrayList<Task> tasks) {
        try {
            FileWriter writer = new FileWriter(path);
            for (Task task : tasks) {
                writer.write(task.getStoreFormat());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Path error");
            return 1;  // Path error
        }
        return 0;
    }
}
