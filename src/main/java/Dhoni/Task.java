package Dhoni;

/**
 * Represents a general task with a description and completion status.
 * This is the base class for all task types (Todo, Deadline, Event).
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task with the given description.
     * The task is initially marked as not done.
     * 
     * @param description the description of the task
     */
    public Task(String description) {
        isDone = false;
        this.description = description;
    }

    /**
     * Marks the task as completed.
     */
    public void completed() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Gets the status string representation of the task.
     * 
     * @return "X" if done, " " if not done
     */
    public String getStatusString() {
        return isDone ? "X" : " ";
    }

    /**
     * Converts the task to a file format string for saving.
     * 
     * @return string representation for file storage
     */
    public String toFileFormat() {
        return "X | " + (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Creates a Task object from a line in the file.
     * Parses the task type and creates the appropriate task subclass.
     * 
     * @param line the line to parse from the file
     * @return the Task object or null if parsing fails
     */
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

    /**
     * Returns the string representation of the task.
     * 
     * @return formatted string showing task status and description
     */
    @Override
    public String toString() {
        return "[" + getStatusString() + "] " + description;
    }
}
