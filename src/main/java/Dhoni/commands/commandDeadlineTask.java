package dhoni.commands;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import dhoni.Constants;
import dhoni.Storage;
import dhoni.tasks.Deadline;
import dhoni.tasks.Task;
import dhoni.tasks.TaskList;

/**
 * Handles adding Deadline task to the task list.
 */
public class CommandDeadlineTask {

    /**
     * Handles adding Deadline task to the task list.
     * If description after Deadline is missing, the task is not added.
     *
     * @param tasks new Deadline task added to tasks
     * @param argument description of the deadline task including by date
     */
    public static String handle(TaskList tasks, String argument, Storage storage) throws Exception {
        assert tasks != null : "Task list should not be null";
        assert argument != null : "Argument should not be null";
        assert storage != null : "Storage should not be null";

        // Parse and validate input
        String[] parts = parseDeadlineArgument(argument);
        if (parts == null) {
            return getDeadlineParseError(argument);
        }

        String description = parts[0].trim();
        String byDate = parts[1].trim();

        // Validate description and date
        String validationError = validateDeadlineInput(description, byDate);
        if (validationError != null) {
            return validationError;
        }

        // Validate date format and logic
        LocalDate parsedDate = validateAndParseDate(byDate);
        if (parsedDate == null) {
            // Check if it's a past date error
            try {
                LocalDate tempDate = LocalDate.parse(byDate, DateTimeFormatter.ofPattern(Constants.DATE_FORMAT));
                if (tempDate.isBefore(LocalDate.now())) {
                    return Constants.ERROR_PAST_DATE;
                }
            } catch (Exception e) {
                // Fall through to invalid date error
            }
            return String.format(Constants.ERROR_DEADLINE_INVALID_DATE, byDate);
        }

        // Check for duplicate tasks
        if (isDuplicateDeadline(tasks, description, byDate)) {
            return String.format(Constants.ERROR_DEADLINE_DUPLICATE, description, byDate);
        }

        // Create and add task
        Task deadline = new Deadline(description, byDate);
        assert deadline != null : "Created deadline should not be null";
        tasks.addTask(deadline);
        storage.saveTasks(tasks);
        return String.format(Constants.MSG_DEADLINE_ADDED, deadline, tasks.getSize());
    }

    /**
     * Parses the deadline argument to extract description and due date.
     * @param argument The full argument string
     * @return Array containing [description, dueDate] if valid, null if invalid
     */
    private static String[] parseDeadlineArgument(String argument) {
        String[] parts = argument.split(" /by ");
        if (parts.length != 2) {
            return null;
        }
        return parts;
    }

    /**
     * Gets the appropriate error message for deadline parsing failures.
     * @param argument The original argument string
     * @return Error message
     */
    private static String getDeadlineParseError(String argument) {
        String[] parts = argument.split(" /by ");
        if (parts.length < 2) {
            return Constants.ERROR_DEADLINE_FORMAT;
        }
        return Constants.ERROR_DEADLINE_MULTIPLE_BY;
    }

    /**
     * Validates the deadline description and due date are not empty.
     * @param description The task description
     * @param byDate The due date string
     * @return Error message if validation fails, null if valid
     */
    private static String validateDeadlineInput(String description, String byDate) {
        if (description.isEmpty()) {
            return Constants.ERROR_DEADLINE_EMPTY_DESC;
        }
        if (byDate.isEmpty()) {
            return Constants.ERROR_DEADLINE_EMPTY_DATE;
        }
        return null;
    }

    /**
     * Validates and parses the date string.
     * @param byDate The date string to parse
     * @return Parsed LocalDate if valid, null if invalid
     */
    private static LocalDate validateAndParseDate(String byDate) {
        try {
            LocalDate parsedDate = LocalDate.parse(byDate, DateTimeFormatter.ofPattern(Constants.DATE_FORMAT));
            if (parsedDate.isBefore(LocalDate.now())) {
                return null; // Past date error handled separately
            }
            return parsedDate;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Checks if a deadline task with the same description and due date already exists.
     * @param tasks The task list to check
     * @param description The task description to check
     * @param byDate The due date to check
     * @return true if duplicate exists, false otherwise
     */
    private static boolean isDuplicateDeadline(TaskList tasks, String description, String byDate) {
        for (Task task : tasks.getTasks()) {
            if (task instanceof Deadline) {
                Deadline existingDeadline = (Deadline) task;
                if (existingDeadline.description().trim().equalsIgnoreCase(description)
                        && existingDeadline.getDueDay().toString().trim().equalsIgnoreCase(byDate)) {
                    return true;
                }
            }
        }
        return false;
    }

}
