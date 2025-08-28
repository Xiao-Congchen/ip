public class DeleteCmd extends Command {
    private final int taskId;

    public DeleteCmd(int taskId) {
        this.taskId = taskId;
    }

    public boolean isBye() {
        return false;
    }

    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        if (taskId > 0) {
            taskList.delete(taskId);
        } else {
            taskList.clear();
        }

    }
}
