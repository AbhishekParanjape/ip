public class Task {
    private String description;
    private Boolean isDone;

    public Task(String description) {
        isDone = false;
        this.description = description;
    }

    public void completed() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    public String getStatusString() {
        return isDone ? "X" : " ";
    }

    @Override
    public String toString() {
        return "[" + getStatusString() + "] " + description;
    }
}
