package dhoni.commands;

import dhoni.Storage;
import dhoni.tasks.Task;
import dhoni.tasks.TaskList;
import dhoni.tasks.Todo;

/**
 * Handles the "todo" command to add a new Todo task to the task list.
 * Validates user input and checks for duplicate tasks before adding.
 */
public class CommandToDoTask {
    /**
     * Handles the addition of a {@code Todo} task to the task list.
     * If description after todo is missing, the task is not added.
     *
     * @param tasks new Todo added to tasks
     * @param argument description everything after "todo"
     */
    public static String handle(TaskList tasks, String argument, Storage storage) throws Exception {
        assert tasks != null : "Task list should not be null";
        assert argument != null : "Argument should not be null";
        assert storage != null : "Storage should not be null";

        String description = argument.trim();
        if (description.isEmpty()) {
            return "Todo description cannot be empty. Usage: todo <description>";
        }

        // Check for duplicate tasks
        for (Task task : tasks.getTasks()) {
            if (task.description().trim().equalsIgnoreCase(description)) {
                return "Duplicate task detected: '" + description + "'. Task already exists.";
            }
        }

        Task todo = new Todo(description);
        assert todo != null : "Created todo should not be null";
        tasks.addTask(todo);
        storage.saveTasks(tasks);
        return "Got it. I've added this task:\n\t" + todo + "\n\tNow you have " + tasks.getSize()
                + " tasks in the list.\n\tAnother run on the board!";
    }
}
