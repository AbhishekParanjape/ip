package Dhoni;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class TaskList {
    private List<Task> tasks;
    
    public TaskList() {
        this.tasks = new ArrayList<>();
    }
    
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }
    
    public void addTask(Task task) {
        tasks.add(task);
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }
    
    /**
     * Deletes the task at the specified index.
     * @param index the index of the task to delete
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
     * @param index the index of the task to mark
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
     * @param index the index of the task to unmark
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
    
    public List<Task> getTasks() {
        return tasks;
    }
    
    public int getSize() {
        return tasks.size();
    }
    
    public Task getTask(int index) throws Exception {
        assert index >= 0 : "Index should not be negative";
        assert index < tasks.size() : "Index should be within bounds";
        if (index < 0 || index >= tasks.size()) {
            throw new Exception("Invalid task number");
        }
        return tasks.get(index);
    }
    
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
     * Finds tasks by keyword in description (case-insensitive)
     * @param keyword the search keyword
     * @return list of tasks matching the keyword
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
