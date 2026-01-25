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
    private static final String FILE_PATH = "./ip/data/dhoni.txt";
    
    /**
     * Saves tasks to file
     * @param tasks the list of tasks to save
     */
    public static void saveTasks(List<Task> tasks) {
        try {
            Files.createDirectories(Paths.get("./ip/data"));
            
            try (FileWriter writer = new FileWriter(FILE_PATH)) {
                for (Task task : tasks) {
                    writer.write(task.toFileFormat() + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
    
    /**
     * Loads tasks from file. Handles missing file gracefully.
     * @return ArrayList of tasks loaded from file
     */
    public static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        
        try {
            if (!Files.exists(Paths.get(FILE_PATH))) {
                return tasks;
            }
            
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Task task = Task.fromFileFormat(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        
        return tasks;
    }
}