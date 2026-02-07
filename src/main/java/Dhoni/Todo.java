package Dhoni;

/**
 * Represents a todo task with a description.
 * This class extends Task and represents a simple task without a due date.
 */
public class Todo extends Task {
    /**
     * Constructs a Todo task with the given description.
     * 
     * @param description the description of the todo task
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Converts the todo task to a file format string for saving.
     * 
     * @return string representation for file storage
     */
    @Override
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Returns the string representation of the todo task.
     * 
     * @return formatted string showing task status and description
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
