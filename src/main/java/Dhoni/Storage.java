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
 * This class provides methods to persist task data and retrieve it later.
 */
public class Storage {
    private String filePath;

    /**
     * Constructs a Storage object with the specified file path.
     * 
     * @param filePath the path to the file where tasks will be stored
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Constructs a Storage object with a default file path.
     * This constructor is provided for backward compatibility.
     */
    public Storage() {
    }
    
    /**
     * Saves the given task list to the file.
     * Creates the necessary directories if they don't exist.
     * 
     * @param tasks the task list to save
     * @throws Exception if there's an error during file operations
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
     * Loads tasks from the file.
     * Returns an empty list if the file doesn't exist.
     * 
     * @return list of tasks loaded from the file
     * @throws Exception if there's an error during file operations
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
