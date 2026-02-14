package Dhoni;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * Parser class handles parsing user input and executing commands.
 * This class provides methods to extract command parts and execute various operations.
 */
public class Parser {
    /**
     * Extracts the command part from the user input.
     * Handles multiple spaces, trailing/leading spaces, and special characters.
     * 
     * @param input the full user input
     * @return the command part of the input in lowercase
     */
    public static String getCommand(String input) {
        assert input != null : "Input should not be null";
        if (input.trim().isEmpty()) {
            return "";
        }
        
        // Normalize whitespace and extract first word
        String[] parts = input.trim().split("\\s+", 2);
        return parts[0].toLowerCase();
    }
    
    /**
     * Extracts the argument part from the user input.
     * Handles multiple spaces, trailing/leading spaces, and preserves the full argument.
     * 
     * @param input the full user input
     * @return the argument part of the input, or empty string if no argument
     */
    public static String getArgument(String input) {
        assert input != null : "Input should not be null";
        if (input.trim().isEmpty()) {
            return "";
        }
        
        // Normalize whitespace and extract argument
        String[] parts = input.trim().split("\\s+", 2);
        return parts.length > 1 ? parts[1].trim() : "";
    }

    /**
     * Validates and parses task index from user argument.
     * 
     * @param argument the user input argument containing task number
     * @param tasks the task list to validate against
     * @return the parsed index (0-based) or -1 if invalid
     */
    private static String handleUnmark(TaskList tasks, String argument, Storage storage) throws Exception {
        assert tasks != null : "Task list should not be null";
        assert argument != null : "Argument should not be null";
        assert storage != null : "Storage should not be null";
        
        if (argument.trim().isEmpty()) {
            return "Please provide a task number to unmark";
        }
        
        try {
            int index = Integer.parseInt(argument.trim()) - 1;
            assert index >= -1 : "Index should not be less than -1";
            if (index < 0) {
                return "Task number must be a positive integer";
            }
            if (index >= tasks.getSize()) {
                return "Task number " + (index + 1) + " does not exist. There are only " + tasks.getSize() + " tasks.";
            }
            if (!tasks.getTask(index).isDone) {
                return "Task " + (index + 1) + " is already not done";
            }
            tasks.getTask(index).unmark();
            storage.saveTasks(tasks);
            return "OK, I've marked this task as not done yet:\n\t" + tasks.getTask(index);
        } catch (NumberFormatException e) {
            return "Please provide a valid task number (e.g., 'unmark 1')";
        }
    }

    /**
     * Handles marking a task as completed.
     * 
     * @param tasks the task list containing the task to mark
     * @param argument the task number to mark (1-based)
     * @param storage the storage to save changes
     * @return response message
     */
    private static String handleMark(TaskList tasks, String argument, Storage storage) throws Exception {
        assert tasks != null : "Task list should not be null";
        assert argument != null : "Argument should not be null";
        assert storage != null : "Storage should not be null";
        
        if (argument.trim().isEmpty()) {
            return "Please provide a task number to mark";
        }
        
        try {
            int index = Integer.parseInt(argument.trim()) - 1;
            assert index >= -1 : "Index should not be less than -1";
            if (index < 0) {
                return "Task number must be a positive integer";
            }
            if (index >= tasks.getSize()) {
                return "Task number " + (index + 1) + " does not exist. There are only " + tasks.getSize() + " tasks.";
            }
            if (tasks.getTask(index).isDone) {
                return "Task " + (index + 1) + " is already done";
            }
            tasks.getTask(index).completed();
            storage.saveTasks(tasks);
            return "Excellent! I've marked this task as done:\n\t" + tasks.getTask(index) + "\n\tThat's a six for productivity!";
        } catch (NumberFormatException e) {
            return "Please provide a valid task number (e.g., 'mark 1')";
        }
    }

    /**
     * Handles deletion of a task from the task list.
     * 
     * @param tasks the task list to delete from
     * @param argument the task number to delete (1-based)
     * @param storage the storage to save changes
     * @return response message
     */
    private static String handleDelete(TaskList tasks, String argument, Storage storage) throws Exception {
        assert tasks != null : "Task list should not be null";
        assert argument != null : "Argument should not be null";
        assert storage != null : "Storage should not be null";
        
        if (argument.trim().isEmpty()) {
            return "Please provide a task number to delete";
        }
        
        try {
            int index = Integer.parseInt(argument.trim()) - 1;
            assert index >= -1 : "Index should not be less than -1";
            if (index < 0) {
                return "Task number must be a positive integer";
            }
            if (index >= tasks.getSize()) {
                return "Task number " + (index + 1) + " does not exist. There are only " + tasks.getSize() + " tasks.";
            }
            Task removed = tasks.getTask(index);
            assert removed != null : "Removed task should not be null";
            tasks.deleteTask(index);
            storage.saveTasks(tasks);
            return "Noted. I've removed this task:\n\t" + removed + "\n\tNow you have " + tasks.getSize() + " tasks in the list.\n\tOut for a duck, but we'll get the next one!";
        } catch (NumberFormatException e) {
            return "Please provide a valid task number (e.g., 'delete 1')";
        }    
    }

    /**
     * Handles displaying the list of tasks to the user.
     * 
     * @param tasks the list of tasks to display
     * @return formatted string representation of the task list
     */
    private static String handleList(TaskList tasks) throws Exception {
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

    /**
     * Handles the addition of a {@code Todo} task to the task list.
     * If description after todo is missing, the task is not added.
     *
     * @param tasks new Todo added to tasks
     * @param argument description everything after "todo"
     */
    private static String handleToDo(TaskList tasks, String argument, Storage storage) throws Exception {
        assert tasks != null : "Task list should not be null";
        assert argument != null : "Argument should not be null";
        assert storage != null : "Storage should not be null";
        
        String description = argument.trim();
        if (description.isEmpty()) {
            return "Todo description cannot be empty. Usage: todo <description>";
        }
        
        // Check for duplicate tasks
        for (Task task : tasks.getTasks()) {
            if (task.description.trim().equalsIgnoreCase(description)) {
                return "Duplicate task detected: '" + description + "'. Task already exists.";
            }
        }
        
        Task todo = new Todo(description);
        assert todo != null : "Created todo should not be null";
        tasks.addTask(todo);
        storage.saveTasks(tasks);
        return "Got it. I've added this task:\n\t" + todo + "\n\tNow you have " + tasks.getSize() + " tasks in the list.\n\tAnother run on the board!";
    }

    /**
     * Handles adding Deadline task to the task list.
     * If description after Deadline is missing, the task is not added.
     * 
     * @param tasks new Deadline task added to tasks
     * @param argument description of the deadline task including by date
     */
    private static String handleDeadlineTask(TaskList tasks, String argument, Storage storage) throws Exception {
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
                if (existingDeadline.description.trim().equalsIgnoreCase(description) && 
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

    /**
     * Handles adding event task to the task list.
     * @param tasks event task added to this list of tasks
     * @param argument description of event consisting of from and to date
     */
    private static String handleEventTask(TaskList tasks, String argument, Storage storage) throws Exception {
        assert tasks != null : "Task list should not be null";
        assert argument != null : "Argument should not be null";
        assert storage != null : "Storage should not be null";
        
        // Handle multiple /from and /to parameters
        String[] fromParts = argument.split(" /from ");
        if (fromParts.length < 2) {
            return "Event format: event <description> /from <time> /to <time>. Missing '/from' parameter.";
        }
        if (fromParts.length > 2) {
            return "Event format: event <description> /from <time> /to <time>. Multiple '/from' parameters detected.";
        }
        
        String[] toParts = fromParts[1].split(" /to ");
        if (toParts.length < 2) {
            return "Event format: event <description> /from <time> /to <time>. Missing '/to' parameter.";
        }
        if (toParts.length > 2) {
            return "Event format: event <description> /from <time> /to <time>. Multiple '/to' parameters detected.";
        }
        
        String description = fromParts[0].trim();
        String fromTime = toParts[0].trim();
        String toTime = toParts[1].trim();
        
        if (description.isEmpty()) {
            return "Event description cannot be empty. Usage: event <description> /from <time> /to <time>";
        }
        if (fromTime.isEmpty()) {
            return "Event start time cannot be empty. Usage: event <description> /from <time> /to <time>";
        }
        if (toTime.isEmpty()) {
            return "Event end time cannot be empty. Usage: event <description> /from <time> /to <time>";
        }
        
        // Validate date formats
        try {
            LocalDate fromDate = LocalDate.parse(fromTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate toDate = LocalDate.parse(toTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            
            // Validate date logic
            if (fromDate.isAfter(toDate)) {
                return "Invalid date range: start date cannot be after end date";
            }
            if (fromDate.isEqual(toDate)) {
                return "Invalid date range: start and end dates cannot be the same";
            }
        } catch (Exception e) {
            return "Invalid date format. Expected yyyy-MM-dd format (e.g., 2023-12-25)";
        }
        
        // Check for duplicate tasks
        for (Task task : tasks.getTasks()) {
            if (task instanceof Event) {
                Event existingEvent = (Event) task;
                if (existingEvent.description.trim().equalsIgnoreCase(description) && 
                    existingEvent.getFrom().toString().trim().equalsIgnoreCase(fromTime) &&
                    existingEvent.getTo().toString().trim().equalsIgnoreCase(toTime)) {
                    return "Duplicate event detected: '" + description + "' from '" + fromTime + "' to '" + toTime + "'. Task already exists.";
                }
            }
        }
        
        Task event = new Event(description, fromTime, toTime);
        assert event != null : "Created event should not be null";
        tasks.addTask(event);
        storage.saveTasks(tasks);
        return "Got it. I've added this task:\n\t" + event + "\n\tNow you have " + tasks.getSize() + " tasks in the list.\n\tEvent scheduled - let's make it count!";
    }
    
    private static String handleFind(TaskList tasks, String argument) throws Exception {
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

    public static boolean execute(String command, TaskList tasks, Storage storage,
                                          Scanner scanner, String argument, String userInput) throws Exception {
        assert command != null : "Command should not be null";
        assert tasks != null : "Task list should not be null";
        assert storage != null : "Storage should not be null";
        assert scanner != null : "Scanner should not be null";
        assert userInput != null : "User input should not be null";
        
        switch (command) {
        case "bye":
            Ui.echo("Bye. Hope to see you again soon!");
            scanner.close();
            return true;
        case "list":
            Ui.echo(handleList(tasks));
            break;
        case "mark":
            Ui.echo(handleMark(tasks, argument, storage));  
            break;
        case "unmark":
            Ui.echo(handleUnmark(tasks, argument, storage));
            break;
        case "delete":
            Ui.echo(handleDelete(tasks, argument, storage));
            break;
        case "todo":
        case "t":
            Ui.echo(handleToDo(tasks, argument, storage));
            break;
        case "deadline":
        case "d":
            Ui.echo(handleDeadlineTask(tasks, argument, storage));
            break;
        case "event":
        case "e":
            Ui.echo(handleEventTask(tasks,argument, storage));
            break;
        case "find":
        case "f":
            Ui.echo(handleFind(tasks, argument));
            break;
        default:
            tasks.addTask(new Todo(userInput));
            storage.saveTasks(tasks);
            Ui.echo(userInput);
        }
        return false;
    }

    /**
     * Executes a command for GUI usage and returns the textual response instead
     * of printing to the console via Ui.echo.
     */
    public static String executeGui(String command, TaskList tasks, Storage storage,
                                    String argument, String userInput) throws Exception {
        assert command != null : "Command should not be null";
        assert tasks != null : "Task list should not be null";
        assert storage != null : "Storage should not be null";
        assert userInput != null : "User input should not be null";
        
        switch (command) {
        case "bye":
            return "Bye. Hope to see you again soon!";
        case "list": 
            return handleList(tasks);
        case "mark": 
            return handleMark(tasks, argument, storage);
        case "unmark": 
            return handleUnmark(tasks, argument, storage);
        case "delete": 
            return handleDelete(tasks, argument, storage);
        case "todo": 
        case "t":
            return handleToDo(tasks, argument, storage);
        case "deadline": 
        case "d":
            return handleDeadlineTask(tasks, argument, storage);
        case "event": 
        case "e":
            return handleEventTask(tasks, argument, storage);
        case "find": 
        case "f":
            return handleFind(tasks, argument);
        default:
            tasks.addTask(new Todo(userInput));
            storage.saveTasks(tasks);
            return userInput;
        }
    }
}
    
    
