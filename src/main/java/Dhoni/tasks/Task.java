package dhoni.tasks;

import dhoni.Constants;

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
        return Constants.TASK_TYPE_GENERIC + Constants.FILE_SEPARATOR
                + (isDone ? Constants.DONE_STATUS_TRUE : Constants.DONE_STATUS_FALSE)
                + Constants.FILE_SEPARATOR + description;
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
        validateLine(line);

        // Split by the file separator pattern - use literal pipe character
        String[] parts = line.split("\\|");
        validateParts(parts);

        String type = parts[0].trim();
        String doneStatus = parts[1].trim();

        boolean isDone = parseDoneStatus(doneStatus);
        validateTaskType(type);

        Task task = createTask(type, parts);

        if (task != null && isDone) {
            task.completed();
        }
        return task;
    }

    /**
     * Validates that the line is not null or empty.
     */
    private static void validateLine(String line) throws Exception {
        if (line == null || line.trim().isEmpty()) {
            throw new Exception("Empty or null line cannot be parsed");
        }
    }

    /**
     * Validates that the line has sufficient parts.
     */
    private static void validateParts(String[] parts) throws Exception {
        if (parts.length < 2) {
            throw new Exception("Invalid task format: insufficient parts");
        }
    }

    /**
     * Parses and validates the done status.
     */
    private static boolean parseDoneStatus(String doneStatus) throws Exception {
        if (doneStatus.equals(Constants.DONE_STATUS_TRUE)) {
            return true;
        } else if (doneStatus.equals(Constants.DONE_STATUS_FALSE)) {
            return false;
        } else {
            throw new Exception("Invalid done status: " + doneStatus + ". Expected 0 or 1");
        }
    }

    /**
     * Validates the task type.
     */
    private static void validateTaskType(String type) throws Exception {
        if (!type.matches("[X|T|D|E]")) {
            throw new Exception("Invalid task type: " + type + ". Expected X, T, D, or E");
        }
    }

    /**
     * Creates the appropriate task based on type.
     */
    private static Task createTask(String type, String[] parts) throws Exception {
        switch (type) {
        case Constants.TASK_TYPE_GENERIC:
            validateMinParts(parts, 3, "Invalid task format: missing description");
            return new Task(parts[2].trim());
        case Constants.TASK_TYPE_TODO:
            validateMinParts(parts, 3, "Invalid todo format: missing description");
            return new Todo(parts[2].trim());
        case Constants.TASK_TYPE_DEADLINE:
            validateMinParts(parts, 4, "Invalid deadline format: missing description or date");
            return new Deadline(parts[2].trim(), parts[3].trim());
        case Constants.TASK_TYPE_EVENT:
            validateMinParts(parts, 5, "Invalid event format: missing description, start time, or end time");
            return new Event(parts[2].trim(), parts[3].trim(), parts[4].trim());
        default:
            throw new Exception("Unknown task type: " + type);
        }
    }

    /**
     * Validates minimum number of parts for a task type.
     */
    private static void validateMinParts(String[] parts, int minParts, String errorMessage) throws Exception {
        if (parts.length < minParts) {
            throw new Exception(errorMessage);
        }
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
