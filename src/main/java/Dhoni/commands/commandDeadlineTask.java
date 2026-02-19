package Dhoni.commands;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import Dhoni.tasks.Deadline;

import Dhoni.tasks.Task;
import Dhoni.tasks.TaskList;

import Dhoni.Storage;

public class commandDeadlineTask {

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
            return "Deadline format: deadline <description> /by <date>. Missing '/by' parameter.";
        }
        if (parts.length > 2) {
            return "Deadline format: deadline <description> /by <date>. Multiple '/by' parameters detected.";
        }
        
        String description = parts[0].trim();
        String byDate = parts[1].trim();
        
        if (description.isEmpty()) {
            return "Deadline description cannot be empty. Usage: deadline <description> /by <date>";
        }
        if (byDate.isEmpty()) {
            return "Deadline date cannot be empty. Usage: deadline <description> /by <date>";
        }
        
        // Validate date format
        try {
            LocalDate.parse(byDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            return "Invalid date format: '" + byDate + "'. Expected yyyy-MM-dd format (e.g., 2023-12-25)";
        }
        
        // Check for duplicate tasks
        for (Task task : tasks.getTasks()) {
            if (task instanceof Deadline) {
                Deadline existingDeadline = (Deadline) task;
                if (existingDeadline.description().trim().equalsIgnoreCase(description) && 
                    existingDeadline.getDueDay().toString().trim().equalsIgnoreCase(byDate)) {
                    return "Duplicate deadline detected: '" + description + "' due '" + byDate + "'. Task already exists.";
                }
            }
        }
        
        Task deadline = new Deadline(description, byDate);
        assert deadline != null : "Created deadline should not be null";
        tasks.addTask(deadline);
        storage.saveTasks(tasks);
        return "Got it. I've added this task:\n\t" + deadline + "\n\tNow you have " + tasks.getSize() + " tasks in the list.\n\tDeadline set - time to finish strong!";
    }
    
}
