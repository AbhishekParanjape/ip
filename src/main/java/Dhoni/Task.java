package Dhoni;
/**
 * Represents a general task with a description and completion status.
 */

public class Task {
    protected String description;
    protected boolean isDone;

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

    public String toFileFormat() {
        return "X | " + (isDone ? "1" : "0") + " | " + description;
    }
    
    public static Task fromFileFormat(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 2) return null;
        
        String type = parts[0].trim();
        boolean isDone = parts[1].trim().equals("1");
        
        Task task = null;
        switch (type) {
        case "X":
            task = new Task(parts[2]);
            break;
        case "T":
            task = new Todo(parts[2]);
            break;
        case "D":
            if (parts.length >= 4) {
                task = new Deadline(parts[2], parts[3]);
            }
            break;
        case "E":
            if (parts.length >= 5) {
                task = new Event(parts[2], parts[3], parts[4]);
            }
            break;
        }
        
        if (task != null && isDone) {
            task.completed();
        }
        return task;
    }

    @Override
    public String toString() {
        return "[" + getStatusString() + "] " + description;
    }
}
