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

        // Parse and validate input
        String[] parts = parseEventArgument(argument);
        if (parts == null) {
            return getEventParseError(argument);
        }

        String description = parts[0].trim();
        String fromTime = parts[1].trim();
        String toTime = parts[2].trim();

        // Validate description and times
        String validationError = validateEventInput(description, fromTime, toTime);
        if (validationError != null) {
            return validationError;
        }

        // Validate date formats and logic
        String dateError = validateEventDates(fromTime, toTime);
        if (dateError != null) {
            return dateError;
        }

        // Check for duplicate tasks
        if (isDuplicateEvent(tasks, description, fromTime, toTime)) {
            return String.format(dhoni.Constants.ERROR_EVENT_DUPLICATE, description, fromTime, toTime);
        }

        // Create and add task
        Task event = new Event(description, fromTime, toTime);
        assert event != null : "Created event should not be null";
        tasks.addTask(event);
        storage.saveTasks(tasks);
        return String.format(dhoni.Constants.MSG_EVENT_ADDED, event, tasks.getSize());
    }

    /**
     * Parses the event argument to extract description, start time, and end time.
     * @param argument The full argument string
     * @return Array containing [description, fromTime, toTime] if valid, null if invalid
     */
    private static String[] parseEventArgument(String argument) {
        String[] fromParts = argument.split(" /from ");
        if (fromParts.length != 2) {
            return null;
        }

        String[] toParts = fromParts[1].split(" /to ");
        if (toParts.length != 2) {
            return null;
        }

        return new String[]{fromParts[0], toParts[0], toParts[1]};
    }

    /**
     * Gets the appropriate error message for event parsing failures.
     * @param argument The original argument string
     * @return Error message
     */
    private static String getEventParseError(String argument) {
        String[] fromParts = argument.split(" /from ");
        if (fromParts.length < 2) {
            return dhoni.Constants.ERROR_EVENT_FORMAT;
        }
        if (fromParts.length > 2) {
            return dhoni.Constants.ERROR_EVENT_MULTIPLE_FROM;
        }

        String[] toParts = fromParts[1].split(" /to ");
        if (toParts.length < 2) {
            return dhoni.Constants.ERROR_EVENT_FORMAT_TO;
        }
        return dhoni.Constants.ERROR_EVENT_MULTIPLE_TO;
    }

    /**
     * Validates the event description, start time, and end time are not empty.
     * @param description The task description
     * @param fromTime The start time string
     * @param toTime The end time string
     * @return Error message if validation fails, null if valid
     */
    private static String validateEventInput(String description, String fromTime, String toTime) {
        if (description.isEmpty()) {
            return dhoni.Constants.ERROR_EVENT_EMPTY_DESC;
        }
        if (fromTime.isEmpty()) {
            return dhoni.Constants.ERROR_EVENT_EMPTY_FROM;
        }
        if (toTime.isEmpty()) {
            return dhoni.Constants.ERROR_EVENT_EMPTY_TO;
        }
        return null;
    }

    /**
     * Validates and parses the date strings and checks date logic.
     * @param fromTime The start date string
     * @param toTime The end date string
     * @return Error message if validation fails, null if valid
     */
    private static String validateEventDates(String fromTime, String toTime) {
        try {
            LocalDate fromDate = LocalDate.parse(fromTime, DateTimeFormatter.ofPattern(dhoni.Constants.DATE_FORMAT));
            LocalDate toDate = LocalDate.parse(toTime, DateTimeFormatter.ofPattern(dhoni.Constants.DATE_FORMAT));

            // Validate date logic
            if (fromDate.isBefore(LocalDate.now())) {
                return dhoni.Constants.ERROR_EVENT_PAST_DATE;
            }
            if (toDate.isBefore(LocalDate.now())) {
                return dhoni.Constants.ERROR_EVENT_PAST_DATE;
            }
            if (fromDate.isAfter(toDate)) {
                return dhoni.Constants.ERROR_EVENT_DATE_RANGE;
            }
            if (fromDate.isEqual(toDate)) {
                return dhoni.Constants.ERROR_EVENT_SAME_DATE;
            }
        } catch (Exception e) {
            return dhoni.Constants.ERROR_EVENT_INVALID_DATE;
        }
        return null;
    }

    /**
     * Checks if an event task with the same description, start time, and end time already exists.
     * @param tasks The task list to check
     * @param description The task description to check
     * @param fromTime The start time to check
     * @param toTime The end time to check
     * @return true if duplicate exists, false otherwise
     */
    private static boolean isDuplicateEvent(TaskList tasks, String description, String fromTime, String toTime) {
        for (Task task : tasks.getTasks()) {
            if (task instanceof Event) {
                Event existingEvent = (Event) task;
                if (existingEvent.description().trim().equalsIgnoreCase(description)
                        && existingEvent.getFrom().toString().trim().equalsIgnoreCase(fromTime)
                        && existingEvent.getTo().toString().trim().equalsIgnoreCase(toTime)) {
                    return true;
                }
            }
        }
        return false;
    }
}
