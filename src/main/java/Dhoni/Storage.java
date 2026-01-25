package Dhoni;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Storage handles saving and loading tasks to and from a file.
 */
public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public Storage() {
    }
    
    /**
     * Saves tasks to file
     * @param tasks the list of tasks to save
     */
    public void saveTasks(TaskList tasks) throws Exception {
        try {
            Files.createDirectories(Paths.get(filePath).getParent());
            List<Task> taskList = tasks.getTasks();
            
            try (FileWriter writer = new FileWriter(filePath)) {
                for (Task task : taskList) {
                    writer.write(task.toFileFormat() + "\n");
                }
            }
        } catch (IOException e) {
            throw new Exception("Error saving tasks: " + e.getMessage());
        }
    }
    
    /**
     * Loads tasks from file. Handles missing file gracefully.
     * @return ArrayList of tasks loaded from file
     */
    public List<Task> loadTasks() throws Exception {
        List<Task> tasks = new ArrayList<>();
        
        try {
            if (!Files.exists(Paths.get(filePath))) {
                return tasks;
            }
            
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Task task = Task.fromFileFormat(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                }
            }
        } catch (IOException e) {
            throw new Exception("Error loading tasks: " + e.getMessage());
        }
        
        return tasks;
    }
}