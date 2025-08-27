public abstract class Task {
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

    public static Task createAppropriateTask(String[] variables) {
        String taskType = variables[0];
        boolean isDone = variables[1].equals("1");
        String desc = variables[2];

        switch (taskType){
        case "T":
            return new ToDo(desc, isDone);
        case "D":
            return new Deadline(desc, isDone, variables[3]);
        case "E":
            return new Event(desc, isDone, variables[3], variables[4]);
        }
        return null;
    }

    public abstract String getStoreFormat();

   @Override
    public String toString() {
        return String.format("[%s] %s", getStat(), this.desc);
   }
}
