public class Task {
    protected String desc;
    protected boolean isDone;

    public Task(String desc) {
        this.desc = desc;
        this.isDone = false;
    }

    private String getStat() {
        return (isDone ? "X" : " ");
    }

    public void setStat(Boolean status) {
        this.isDone = status;
    }

    /**
     * Returns a readable, easy-to-parse format for storage use
     * @return String representation of task
     */
    public String getStoreFormat() {
        // 1 means done(marked), 0 means not done(unmarked)
        return String.format("T | %d | %s", isDone ? 1 : 0, desc);
    }

   @Override
    public String toString() {
        return String.format("[%s] %s", getStat(), this.desc);
   }
}
