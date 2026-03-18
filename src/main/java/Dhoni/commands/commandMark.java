package dhoni.commands;

import dhoni.Storage;
import dhoni.tasks.TaskList;

/**
 * Handles marking a task as completed.
 */
public class CommandMark {

    /**
     * Handles marking a task as completed.
     *
     * @param tasks the task list containing the task to mark
     * @param argument the task number to mark (1-based)
     * @param storage the storage to save changes
     * @return response message
     */
    public static String handle(TaskList tasks, String argument, Storage storage) throws Exception {
        assert tasks != null : "Task list should not be null";
        assert argument != null : "Argument should not be null";
        assert storage != null : "Storage should not be null";

        if (argument.trim().isEmpty()) {
            return "Please provide a task number to mark";
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
            if (tasks.getTask(index).isDone()) {
                return "Task " + (index + 1) + " is already done";
            }
            tasks.getTask(index).completed();
            storage.saveTasks(tasks);
            return "Excellent! I've marked this task as done:\n\t"
                + tasks.getTask(index) + "\n\tThat's a six for productivity!";
        } catch (NumberFormatException e) {
            return "Please provide a valid task number (e.g., 'mark 1')";
        }
    }
}
