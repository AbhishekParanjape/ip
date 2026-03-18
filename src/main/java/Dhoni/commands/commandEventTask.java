package dhoni.commands;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import dhoni.Storage;
import dhoni.tasks.Event;
import dhoni.tasks.Task;
import dhoni.tasks.TaskList;

/**
 * Handles the "event" command to add a new Event task to the task list.
 */
public class CommandEventTask {
    /**
     * Handles adding event task to the task list.
     * @param tasks event task added to this list of tasks
     * @param argument description of event consisting of from and to date
     */
    public static String handle(TaskList tasks, String argument, Storage storage) throws Exception {
        assert tasks != null : "Task list should not be null";
        assert argument != null : "Argument should not be null";
        assert storage != null : "Storage should not be null";

        // Handle multiple /from and /to parameters
        String[] fromParts = argument.split(" /from ");
        if (fromParts.length < 2) {
            return "Event format: event <description> /from <time> /to <time>. Missing '/from' parameter.";
        }
        if (fromParts.length > 2) {
            return "Event format: event <description> /from <time> /to <time>. Multiple '/from' parameters detected.";
        }

        String[] toParts = fromParts[1].split(" /to ");
        if (toParts.length < 2) {
            return "Event format: event <description> /from <time> /to <time>. Missing '/to' parameter.";
        }
        if (toParts.length > 2) {
            return "Event format: event <description> /from <time> /to <time>. Multiple '/to' parameters detected.";
        }

        String description = fromParts[0].trim();
        String fromTime = toParts[0].trim();
        String toTime = toParts[1].trim();

        if (description.isEmpty()) {
            return "Event description cannot be empty. Usage: event <description> /from <time> /to <time>";
        }
        if (fromTime.isEmpty()) {
            return "Event start time cannot be empty. Usage: event <description> /from <time> /to <time>";
        }
        if (toTime.isEmpty()) {
            return "Event end time cannot be empty. Usage: event <description> /from <time> /to <time>";
        }

        // Validate date formats
        try {
            LocalDate fromDate = LocalDate.parse(fromTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate toDate = LocalDate.parse(toTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // Validate date logic
            if (fromDate.isAfter(toDate)) {
                return "Invalid date range: start date cannot be after end date";
            }
            if (fromDate.isEqual(toDate)) {
                return "Invalid date range: start and end dates cannot be the same";
            }
        } catch (Exception e) {
            return "Invalid date format. Expected yyyy-MM-dd format (e.g., 2023-12-25)";
        }

        // Check for duplicate tasks
        for (Task task : tasks.getTasks()) {
            if (task instanceof Event) {
                Event existingEvent = (Event) task;
                if (existingEvent.description().trim().equalsIgnoreCase(description)
                        && existingEvent.getFrom().toString().trim().equalsIgnoreCase(fromTime)
                        && existingEvent.getTo().toString().trim().equalsIgnoreCase(toTime)) {
                    return "Duplicate event detected: '"
                        + description + "' from '" + fromTime
                        + "' to '" + toTime + "'. Task already exists.";
                }
            }
        }

        Task event = new Event(description, fromTime, toTime);
        assert event != null : "Created event should not be null";
        tasks.addTask(event);
        storage.saveTasks(tasks);
        return "Got it. I've added this task:\n\t" + event + "\n\tNow you have " + tasks.getSize()
                + " tasks in the list.\n\tEvent scheduled - let's make it count!";
    }
}
