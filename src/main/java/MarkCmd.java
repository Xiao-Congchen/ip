public class MarkCmd extends Command {
    private final int taskId;
    private final boolean isDone;

    public MarkCmd(int taskId, boolean isDone) {
        super();
        this.taskId = taskId;
        this.isDone = isDone;
    }

    public boolean isBye() {
        return false;
    }

    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        taskList.toggleMark(taskId, isDone);
    }
}
