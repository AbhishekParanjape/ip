package Dhoni.tasks;

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

    public boolean isDone() {
        return this.isDone;
    }

    public String description() {
        return this.description;
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
     * @throws Exception if the line format is invalid
     */
    public static Task fromFileFormat(String line) throws Exception {
        if (line == null || line.trim().isEmpty()) {
            throw new Exception("Empty or null line cannot be parsed");
        }
        
        String[] parts = line.split(" \\| ");
        if (parts.length < 2) {
            throw new Exception("Invalid task format: insufficient parts");
        }
        
        String type = parts[0].trim();
        String doneStatus = parts[1].trim();
        
        // Validate done status
        boolean isDone;
        if (doneStatus.equals("1")) {
            isDone = true;
        } else if (doneStatus.equals("0")) {
            isDone = false;
        } else {
            throw new Exception("Invalid done status: " + doneStatus + ". Expected 0 or 1");
        }
        
        // Validate task type
        if (!type.matches("[X|T|D|E]")) {
            throw new Exception("Invalid task type: " + type + ". Expected X, T, D, or E");
        }
        
        Task task = null;
        switch (type) {
        case "X":
            if (parts.length < 3) {
                throw new Exception("Invalid task format: missing description");
            }
            task = new Task(parts[2]);
            break;
        case "T":
            if (parts.length < 3) {
                throw new Exception("Invalid todo format: missing description");
            }
            task = new Todo(parts[2]);
            break;
        case "D":
            if (parts.length < 4) {
                throw new Exception("Invalid deadline format: missing description or date");
            }
            task = new Deadline(parts[2], parts[3]);
            break;
        case "E":
            if (parts.length < 5) {
                throw new Exception("Invalid event format: missing description, start time, or end time");
            }
            task = new Event(parts[2], parts[3], parts[4]);
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
