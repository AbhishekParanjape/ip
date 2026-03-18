package dhoni.commands;

import dhoni.Storage;
import dhoni.tasks.TaskList;

/**
 * Handles the "unmark" command to mark a task as not done.
 * Validates user input and updates the task list and storage accordingly.
 */
public class CommandUnmark {
    /**
     * Validates and parses task index from user argument.
     *
     * @param argument the user input argument containing task number
     * @param tasks the task list to validate against
     * @return the parsed index (0-based) or -1 if invalid
     */
    public static String handle(TaskList tasks, String argument, Storage storage) throws Exception {
        assert tasks != null : "Task list should not be null";
        assert argument != null : "Argument should not be null";
        assert storage != null : "Storage should not be null";

        if (argument.trim().isEmpty()) {
            return "Please provide a task number to unmark";
        }

        try {
            int index = Integer.parseInt(argument.trim()) - 1;
            assert index >= -1 : "Index should not be less than -1";
            if (index < 0) {
                return "Task number must be a positive integer";
            }
            if (index >= tasks.getSize()) {
                return "Task number " + (index + 1) + " does not exist. There are only " + tasks.getSize()
                        + " tasks.";
            }
            if (!tasks.getTask(index).isDone()) {
                return "Task " + (index + 1) + " is already not done";
            }
            tasks.getTask(index).unmark();
            storage.saveTasks(tasks);
            return "OK, I've marked this task as not done yet:\n\t" + tasks.getTask(index);
        } catch (NumberFormatException e) {
            return "Please provide a valid task number (e.g., 'unmark 1')";
        }
    }

}
