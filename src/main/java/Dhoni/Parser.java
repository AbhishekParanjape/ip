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
     * 
     * @param input the full user input
     * @return the command part of the input in lowercase
     */
    public static String getCommand(String input) {
        return input.split(" ", 2)[0].toLowerCase();
    }
    
    /**
     * Extracts the argument part from the user input.
     * 
     * @param input the full user input
     * @return the argument part of the input, or empty string if no argument
     */
    public static String getArgument(String input) {
        String[] parts = input.split(" ", 2);
        return parts.length > 1 ? parts[1] : "";
    }

    /**
     * Validates and parses task index from user argument.
     * 
     * @param argument the user input argument containing task number
     * @param tasks the task list to validate against
     * @return the parsed index (0-based) or -1 if invalid
     */
    private static int parseTaskIndex(String argument, TaskList tasks) {
        try {
            int index = Integer.parseInt(argument.trim()) - 1;
            if (index < 0 || index >= tasks.getSize()) {
                return -1;
            }
            return index;
        } catch (NumberFormatException e) {
            return -1;
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
        int index = parseTaskIndex(argument, tasks);
        if (index == -1) {
            return "Invalid task number";
        }
        
        tasks.getTask(index).completed();
        storage.saveTasks(tasks);
        return "Nice! I've marked this task as done:\n\t" + tasks.getTask(index);
    }

    /**
     * Handles marking a task as not completed.
     * 
     * @param tasks the task list containing the task to unmark
     * @param argument the task number to unmark (1-based)
     * @param storage the storage to save changes
     * @return response message
     */
    private static String handleUnmark(TaskList tasks, String argument, Storage storage) throws Exception {
        int index = parseTaskIndex(argument, tasks);
        if (index == -1) {
            return "Invalid task number";
        }
        
        tasks.getTask(index).unmark();
        storage.saveTasks(tasks);
        return "OK, I've marked this task as not done yet:\n\t" + tasks.getTask(index);
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
        int index = parseTaskIndex(argument, tasks);
        if (index == -1) {
            return "Invalid task number";
        }
        
        Task removed = tasks.getTask(index);
        tasks.deleteTask(index);
        storage.saveTasks(tasks);
        return "Noted. I've removed this task:\n\t" + removed + "\n\tNow you have " + tasks.getSize() + " tasks in the list.";
    }

    /**
     * Handles displaying the list of tasks to the user.
     * 
     * @param tasks the list of tasks to display
     * @return formatted string representation of the task list
     */
    private static String handleList(TaskList tasks) throws Exception {
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
        if (argument.trim().isEmpty()) {
            return "Todo description cannot be empty";
        }
        Task todo = new Todo(argument.trim());
        tasks.addTask(todo);
        storage.saveTasks(tasks);
        return "Got it. I've added this task:\n\t" + todo + "\n\tNow you have " + tasks.getSize() + " tasks in the list.";
    }

    /**
     * Handles adding Deadline task to the task list.
     * If description after Deadline is missing, the task is not added.
     * 
     * @param tasks new Deadline task added to tasks
     * @param argument description of the deadline task including by date
     */
    private static String handleDeadlineTask(TaskList tasks, String argument, Storage storage) throws Exception {
        String[] parts = argument.split(" /by ");
        if (parts.length < 2 || parts[0].trim().isEmpty()) {
            return "Deadline format: deadline <description> /by <date>";
        }
        Task deadline = new Deadline(parts[0].trim(), parts[1].trim());
        tasks.addTask(deadline);
        storage.saveTasks(tasks);
        return "Got it. I've added this task:\n\t" + deadline + "\n\tNow you have " + tasks.getSize() + " tasks in the list.";
    }

    /**
     * Handles adding event task to the task list.
     * @param tasks event task added to this list of tasks
     * @param argument description of event consisting of from and to date
     */
    private static String handleEventTask(TaskList tasks, String argument, Storage storage) throws Exception {
        String[] parts = argument.split(" /from ");
        if (parts.length < 2) {
            return "Event format: event <description> /from <time> /to <time>";
        }
        
        String[] timeParts = parts[1].split(" /to ");
        if (timeParts.length < 2) {
            return "Event format: event <description> /from <time> /to <time>";
        }
        
        Task event = new Event(parts[0].trim(), timeParts[0].trim(), timeParts[1].trim());
        tasks.addTask(event);
        storage.saveTasks(tasks);
        return "Got it. I've added this task:\n\t" + event + "\n\tNow you have " + tasks.getSize() + " tasks in the list.";
    }
    
    private static String handleFind(TaskList tasks, String argument) throws Exception {
        if (argument.trim().isEmpty()) {
            return "Usage: find <keyword or yyyy-MM-dd>/<additional keywords or dates>";
        }
        // Try to parse as date first
        //splice argument by '/'' and parse each line as date
        String[] arg = argument.split("/");
        try {
            
            return handleFindByDate(tasks, arg[0].trim().lines()
                    .map(dateStr -> LocalDate.parse(dateStr.trim(), DateTimeFormatter.ISO_LOCAL_DATE))
                    .toArray(LocalDate[]::new) );
        } catch (Exception e) {
            // Not a date, search by keyword
            return handleFindByKeyword(tasks, arg);
        }
    }

    private static String handleFindByDate(TaskList tasks, LocalDate... targetDates) throws Exception {
        StringBuilder sb = new StringBuilder("Tasks on " + targetDates + ":");
        List<Task> foundTasks = tasks.findByDate(targetDates);
        
        if (foundTasks.isEmpty()) {
            sb.append("\n\tNo tasks found on this date.");
        } else {
            for (int i = 0; i < foundTasks.size(); i++) {
                sb.append("\n\t").append(foundTasks.get(i));
            }
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
            return ("No tasks found matching keyword: " + keywords);
        } else {
            StringBuilder sb = new StringBuilder("Tasks matching \"" + keywords + "\":\n");
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
            Ui.echo(handleToDo(tasks, argument, storage));
            
            break;
        case "deadline":
            Ui.echo(handleDeadlineTask(tasks, argument, storage));
            break;
        case "event":
            Ui.echo(handleEventTask(tasks,argument, storage));
            break;
        case "find":
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
            return handleToDo(tasks, argument, storage);
        case "deadline": 
            return handleDeadlineTask(tasks, argument, storage);
        case "event": 
            return handleEventTask(tasks, argument, storage);
        case "find": 
            return handleFind(tasks, argument);
        default:
            tasks.addTask(new Todo(userInput));
            storage.saveTasks(tasks);
            return userInput;
        }
    }
}
    
    
