package Dhoni.commands;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import Dhoni.tasks.Task;
import Dhoni.tasks.TaskList;

public class commandFind {
    public static String handle(TaskList tasks, String argument) throws Exception {
        assert tasks != null : "Task list should not be null";
        assert argument != null : "Argument should not be null";
        
        String trimmedArg = argument.trim();
        if (trimmedArg.isEmpty()) {
            return "Usage: find <keyword or yyyy-MM-dd>/<additional keywords or dates>";
        }
        
        // Try to parse as date first
        String[] arg = trimmedArg.split("/");
        try {
            // Validate date format before parsing
            for (String dateStr : arg) {
                if (!dateStr.trim().matches("\\d{4}-\\d{2}-\\d{2}")) {
                    throw new Exception("Invalid date format: " + dateStr + ". Expected yyyy-MM-dd");
                }
            }
            
            return handleFindByDate(tasks, arg[0].trim().lines()
                    .map(dateStr -> LocalDate.parse(dateStr.trim(), DateTimeFormatter.ISO_LOCAL_DATE))
                    .toArray(LocalDate[]::new));
        } catch (Exception e) {
            // Not a date, search by keyword
            return handleFindByKeyword(tasks, arg);
        }
    }

    private static String handleFindByDate(TaskList tasks, LocalDate... targetDates) throws Exception {
        StringBuilder sb = new StringBuilder("Tasks on:");
        List<Task> foundTasks = tasks.findByDate(targetDates);
        
        if (foundTasks.isEmpty()) {
            sb.append("\n\tNo tasks found on this date.");
        } else {
            // Use streams to format the task list more elegantly
            String taskList = foundTasks.stream()
                    .map(Task::toString)
                    .reduce((task1, task2) -> task1 + "\n\t" + task2)
                    .orElse("");
            sb.append("\n\t").append(taskList);
        }
        return sb.toString();
    }

    /**
     * Finds tasks by keyword in description (case-insensitive)
     * @param keyword the search keyword
     * @return list of tasks matching the keyword
     */
    private static String handleFindByKeyword(TaskList tasks, String... keywords) throws Exception {
        List<Task> foundTasks = tasks.findByKeyword(keywords);
        
        if (foundTasks.isEmpty()) {
            return ("No tasks found matching keyword. ");
        } else {
            StringBuilder sb = new StringBuilder("Tasks matching: \n");
            for (int i = 0; i < foundTasks.size(); i++) {
                sb.append("\t").append(foundTasks.get(i));
                if (i < foundTasks.size() - 1) {
                    sb.append("\n");
                }
            }
            return sb.toString();
        }
    }
}
