package Dhoni.commands;

import Dhoni.Storage;
import Dhoni.tasks.Task;
import Dhoni.tasks.TaskList;

/**
 * Handles deletion of a task from the task list.
 */
public class CommandDelete {
    /**
     * Handles deletion of a task from the task list.
     *
     * @param tasks the task list to delete from
     * @param argument the task number to delete (1-based)
     * @param storage the storage to save changes
     * @return response message
     */
    public static String handle(TaskList tasks, String argument, Storage storage) throws Exception {
        assert tasks != null : "Task list should not be null";
        assert argument != null : "Argument should not be null";
        assert storage != null : "Storage should not be null";

        if (argument.trim().isEmpty()) {
            return "Please provide a task number to delete";
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
            Task removed = tasks.getTask(index);
            assert removed != null : "Removed task should not be null";
            tasks.deleteTask(index);
            storage.saveTasks(tasks);
            return "Noted. I've removed this task:\n\t" + removed + "\n\tNow you have " + tasks.getSize()
                + " tasks in the list.\n\tOut for a duck, but we'll get the next one!";
        } catch (NumberFormatException e) {
            return "Please provide a valid task number (e.g., 'delete 1')";
        }
    }
}
