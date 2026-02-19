package Dhoni;

import java.util.Scanner;
import Dhoni.tasks.TaskList;
import Dhoni.tasks.Todo;
import Dhoni.ui.Ui;

import Dhoni.commands.commandDeadlineTask;
import Dhoni.commands.commandEventTask;
import Dhoni.commands.commandDelete;
import Dhoni.commands.commandFind;
import Dhoni.commands.commandList;
import Dhoni.commands.commandMark;
import Dhoni.commands.commandToDoTask;
import Dhoni.commands.commandUnmark;

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
            Ui.echo(commandList.handle(tasks));
            break;
        case "mark":
            Ui.echo(commandMark.handle(tasks, argument, storage));  
            break;
        case "unmark":
            Ui.echo(commandUnmark.handle(tasks, argument, storage));
            break;
        case "delete":
            Ui.echo(commandDelete.handle(tasks, argument, storage));
            break;
        case "todo":
        case "t":
            Ui.echo(commandToDoTask.handle(tasks, argument, storage));
            break;
        case "deadline":
        case "d":
            Ui.echo(commandDeadlineTask.handle(tasks, argument, storage));
            break;
        case "event":
        case "e":
            Ui.echo(commandEventTask.handle(tasks,argument, storage));
            break;
        case "find":
        case "f":
            Ui.echo(commandFind.handle(tasks, argument));
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
            return commandList.handle(tasks);
        case "mark": 
            return commandMark.handle(tasks, argument, storage);
        case "unmark": 
            return commandUnmark.handle(tasks, argument, storage);
        case "delete": 
            return commandDelete.handle(tasks, argument, storage);
        case "todo": 
        case "t":
            return commandToDoTask.handle(tasks, argument, storage);
        case "deadline": 
        case "d":
            return commandDeadlineTask.handle(tasks, argument, storage);
        case "event": 
        case "e":
            return commandEventTask.handle(tasks, argument, storage);
        case "find": 
        case "f":
            return commandFind.handle(tasks, argument);
        default:
            tasks.addTask(new Todo(userInput));
            storage.saveTasks(tasks);
            return userInput;
        }
    }
}
