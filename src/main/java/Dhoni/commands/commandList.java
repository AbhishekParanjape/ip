package dhoni.commands;

import dhoni.tasks.TaskList;

/**
 * Handles displaying the list of tasks to the user.
 */
public class CommandList {
    /**
     * Handles displaying the list of tasks to the user.
     *
     * @param tasks the list of tasks to display
     * @return formatted string representation of the task list
     * @throws Exception if there's an error during display
     */
    public static String handle(TaskList tasks) throws Exception {
        assert tasks != null : "Task list should not be null";
        if (tasks.isEmpty()) {
            return "Here are the tasks in your list:\n\t(no tasks yet)";
        }

        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.getSize(); i++) {
            sb.append("\t").append((i + 1)).append(". ").append(tasks.getTask(i).toString());
            if (i < tasks.getSize() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
