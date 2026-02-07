package Dhoni;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * TaskList manages a collection of tasks.
 * This class provides methods to add, remove, mark, unmark, and search tasks.
 */
public class TaskList {
    private List<Task> tasks;
    
    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }
    
    /**
     * Constructs a TaskList with the given list of tasks.
     * 
     * @param tasks the initial list of tasks
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }
    
    /**
     * Adds a task to the task list.
     * 
     * @param task the task to add
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Checks if the task list is empty.
     * 
     * @return true if the task list is empty, false otherwise
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }
    
    /**
     * Deletes the task at the specified index.
     * 
     * @param index the index of the task to delete (0-based)
     * @throws Exception if the index is invalid
     */
    public void deleteTask(int index) throws Exception {
        assert index >= 0 : "Index should not be negative";
        assert index < tasks.size() : "Index should be within bounds";
        if (index < 0 || index >= tasks.size()) {
            throw new Exception("Invalid task number");
        }
        tasks.remove(index);
    }
    
    /**
     * Marks the task at the specified index as done.
     * 
     * @param index the index of the task to mark (0-based)
     * @throws Exception if the index is invalid
     */
    public void markTask(int index) throws Exception {
        assert index >= 0 : "Index should not be negative";
        assert index < tasks.size() : "Index should be within bounds";
        if (index < 0 || index >= tasks.size()) {
            throw new Exception("Invalid task number");
        }
        tasks.get(index).completed();
    }
    
    /**
     * Unmarks the task at the specified index as not done.
     * 
     * @param index the index of the task to unmark (0-based)
     * @throws Exception if the index is invalid
     */
    public void unmarkTask(int index) throws Exception {
        assert index >= 0 : "Index should not be negative";
        assert index < tasks.size() : "Index should be within bounds";
        if (index < 0 || index >= tasks.size()) {
            throw new Exception("Invalid task number");
        }
        tasks.get(index).unmark();
    }
    
    /**
     * Gets the list of all tasks.
     * 
     * @return the list of tasks
     */
    public List<Task> getTasks() {
        return tasks;
    }
    
    /**
     * Gets the number of tasks in the list.
     * 
     * @return the size of the task list
     */
    public int getSize() {
        return tasks.size();
    }
    
    /**
     * Gets the task at the specified index.
     * 
     * @param index the index of the task to retrieve (0-based)
     * @return the task at the specified index
     * @throws Exception if the index is invalid
     */
    public Task getTask(int index) throws Exception {
        assert index >= 0 : "Index should not be negative";
        assert index < tasks.size() : "Index should be within bounds";
        if (index < 0 || index >= tasks.size()) {
            throw new Exception("Invalid task number");
        }
        return tasks.get(index);
    }
    
    /**
     * Finds tasks by date.
     * For Deadline tasks, matches the due date.
     * For Event tasks, matches if the date falls within the event's from-to range.
     * 
     * @param targetDates the dates to search for
     * @return list of tasks matching the specified dates
     */
    public List<Task> findByDate(LocalDate... targetDates) {
        List<Task> result = new ArrayList<>();
        for (LocalDate targetDate : targetDates) {
            for (Task task : tasks) {
                if (task instanceof Deadline) {
                    if (((Deadline) task).getDueDay().equals(targetDate)) {
                        result.add(task);
                    }
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                    if (!event.getFrom().isAfter(targetDate) && !event.getTo().isBefore(targetDate)) {
                        result.add(task);
                    }
                }
            }
        }
        return result;
    }
    
    /**
     * Finds tasks by keyword in description (case-insensitive).
     * 
     * @param keywords the search keywords
     * @return list of tasks matching any of the keywords
     */
    public List<Task> findByKeyword(String... keywords) {
        List<Task> result = new ArrayList<>();
        for (String keyword : keywords) {
            String searchTerm = keyword.toLowerCase();
            for (Task task : tasks) {
                if (task.description.toLowerCase().contains(searchTerm)) {
                    result.add(task);
                }
            }
        }
        return result;
    }
}
