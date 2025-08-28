public abstract class Command {
    public Command() {
    }

    public abstract boolean isBye();

    public abstract void execute(Ui ui, Storage storage, TaskList taskList) throws DuckyExceptions;
}
