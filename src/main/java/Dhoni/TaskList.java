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
    
    public void deleteTask(int index) throws Exception {
        if (index < 0 || index >= tasks.size()) {
            throw new Exception("Invalid task number");
        }
        tasks.remove(index);
    }
    
    public void markTask(int index) throws Exception {
        if (index < 0 || index >= tasks.size()) {
            throw new Exception("Invalid task number");
        }
        tasks.get(index).completed();
    }
    
    public void unmarkTask(int index) throws Exception {
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
        if (index < 0 || index >= tasks.size()) {
            throw new Exception("Invalid task number");
        }
        return tasks.get(index);
    }
    
    public List<Task> findByDate(LocalDate targetDate) {
        List<Task> result = new ArrayList<>();
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
        return result;
    }
}
