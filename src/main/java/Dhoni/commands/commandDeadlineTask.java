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

        // Handle multiple /by parameters
        String[] parts = argument.split(" /by ");
        if (parts.length < 2) {
            return Constants.ERROR_DEADLINE_FORMAT;
        }
        if (parts.length > 2) {
            return Constants.ERROR_DEADLINE_MULTIPLE_BY;
        }

        String description = parts[0].trim();
        String byDate = parts[1].trim();

        if (description.isEmpty()) {
            return Constants.ERROR_DEADLINE_EMPTY_DESC;
        }
        if (byDate.isEmpty()) {
            return Constants.ERROR_DEADLINE_EMPTY_DATE;
        }

        // Validate date format
        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(byDate, DateTimeFormatter.ofPattern(Constants.DATE_FORMAT));
        } catch (Exception e) {
            return String.format(Constants.ERROR_DEADLINE_INVALID_DATE, byDate);
        }

        // Check if date is in the past
        LocalDate today = LocalDate.now();
        if (parsedDate.isBefore(today)) {
            return Constants.ERROR_PAST_DATE;
        }

        // Check for duplicate tasks
        for (Task task : tasks.getTasks()) {
            if (task instanceof Deadline) {
                Deadline existingDeadline = (Deadline) task;
                if (existingDeadline.description().trim().equalsIgnoreCase(description)
                        && existingDeadline.getDueDay().toString().trim().equalsIgnoreCase(byDate)) {
                    return String.format(Constants.ERROR_DEADLINE_DUPLICATE, description, byDate);
                }
            }
        }

        Task deadline = new Deadline(description, byDate);
        assert deadline != null : "Created deadline should not be null";
        tasks.addTask(deadline);
        storage.saveTasks(tasks);
        return String.format(Constants.MSG_DEADLINE_ADDED, deadline, tasks.getSize());
    }

}
