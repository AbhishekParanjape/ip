package dhoni;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import dhoni.tasks.Task;
import dhoni.tasks.TaskList;

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
        validateFilePath(filePath);
        this.filePath = filePath;
    }

    /**
     * Constructs a Storage object with a default file path.
     * This constructor is provided for backward compatibility.
     */
    public Storage() {
    }

    /**
     * Validates the file path.
     */
    private void validateFilePath(String filePath) {
        assert filePath != null : "File path should not be null";
        assert !filePath.trim().isEmpty() : "File path should not be empty";
    }

    /**
     * Saves the given task list to the file.
     * Creates the necessary directories if they don't exist.
     *
     * @param tasks the task list to save
     * @throws Exception if there's an error during file operations
     */
    public void saveTasks(TaskList tasks) throws Exception {
        validateSaveParameters(tasks);
        createParentDirectories();
        writeTasksToFile(tasks.getTasks());
    }

    /**
     * Validates parameters for saving tasks.
     */
    private void validateSaveParameters(TaskList tasks) throws Exception {
        if (tasks == null) {
            throw new Exception("Task list should not be null");
        }
        if (filePath == null) {
            throw new Exception("File path should not be null");
        }
    }

    /**
     * Creates parent directories for the file path.
     */
    private void createParentDirectories() throws IOException {
        Files.createDirectories(Paths.get(filePath).getParent());
    }

    /**
     * Writes tasks to the file.
     */
    private void writeTasksToFile(List<Task> taskList) throws Exception {
        if (taskList == null) {
            throw new Exception("Task list should not be null");
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            for (Task task : taskList) {
                validateTask(task);
                writer.write(task.toFileFormat() + "\n");
            }
        } catch (IOException e) {
            throw new Exception("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Validates a task before writing.
     */
    private void validateTask(Task task) throws Exception {
        if (task == null) {
            throw new Exception("Task should not be null");
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
        validateFilePath(filePath);

        if (!fileExists()) {
            return new ArrayList<>();
        }

        validateFileReadability();
        return readTasksFromFile();
    }

    /**
     * Checks if the file exists.
     */
    private boolean fileExists() {
        return Files.exists(Paths.get(filePath));
    }

    /**
     * Validates that the file is readable.
     */
    private void validateFileReadability() throws Exception {
        if (!Files.isReadable(Paths.get(filePath))) {
            throw new Exception("Cannot read file: " + filePath + ". Check file permissions.");
        }
    }

    /**
     * Reads tasks from the file with error handling.
     */
    private List<Task> readTasksFromFile() throws Exception {
        List<Task> tasks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (shouldSkipLine(line)) {
                    continue;
                }

                try {
                    Task task = Task.fromFileFormat(line);
                    if (task != null) {
                        tasks.add(task);
                    } else {
                        logWarning(lineNumber, line, "Failed to parse task");
                    }
                } catch (Exception e) {
                    logWarning(lineNumber, line, e.getMessage());
                    // Continue processing other lines instead of failing completely
                }
            }
        } catch (IOException e) {
            throw new Exception("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }

    /**
     * Determines if a line should be skipped.
     */
    private boolean shouldSkipLine(String line) {
        return line.trim().isEmpty();
    }

    /**
     * Logs a warning message.
     */
    private void logWarning(int lineNumber, String line, String error) {
        System.err.println("Warning: " + error + " on line " + lineNumber + ": " + line);
    }
}
