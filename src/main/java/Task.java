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

    public void changeStat() {
        this.isDone = !isDone;
    }

   @Override
    public String toString() {
        return String.format("[%s] %s", getStat(), this.desc);
   }
}
