import java.util.ArrayList;

public class AddCmd extends Command {
    private final String type;
    private final ArrayList<Object> vars;

    public AddCmd(String type, ArrayList<Object> vars) {
        super();
        this.type = type;
        this.vars = vars;
    }

    public boolean isBye() {
        return false;
    }

    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        taskList.addTask(type, vars);
    }
}
