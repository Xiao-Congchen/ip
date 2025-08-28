public class ListCmd extends Command {
    public ListCmd () {
        super();
    }
    public boolean isBye() {
        return false;
    }

    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) throws DuckyExceptions {
        taskList.list();
    }
}
