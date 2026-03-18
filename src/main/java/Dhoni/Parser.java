package dhoni;

import java.util.Scanner;

import dhoni.commands.CommandDeadlineTask;
import dhoni.commands.CommandDelete;
import dhoni.commands.CommandEventTask;
import dhoni.commands.CommandFind;
import dhoni.commands.CommandList;
import dhoni.commands.CommandMark;
import dhoni.commands.CommandToDoTask;
import dhoni.commands.CommandUnmark;
import dhoni.tasks.TaskList;
import dhoni.ui.Ui;

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
     * Executes a command for CLI usage.
     *
     * @param command the command to execute
     * @param tasks the task list to operate on
     * @param storage the storage to save changes
     * @param scanner the scanner for user input
     * @param argument the argument for the command
     * @param userInput the full user input
     * @return true if the application should exit, false otherwise
     * @throws Exception if there's an error during execution
     */
    public static boolean execute(String command, TaskList tasks, Storage storage,
                                          Scanner scanner, String argument, String userInput) throws Exception {
        assert command != null : "Command should not be null";
        assert tasks != null : "Task list should not be null";
        assert storage != null : "Storage should not be null";
        assert scanner != null : "Scanner should not be null";
        assert userInput != null : "User input should not be null";

        switch (command) {
        case "bye":
            Ui.echo(Constants.MSG_BYE);
            // Force termination for CLI
            if (scanner != null) {
                scanner.close();
            }
            return true;
        case "list":
            Ui.echo(CommandList.handle(tasks));
            break;
        case "mark":
            Ui.echo(CommandMark.handle(tasks, argument, storage));
            break;
        case "unmark":
            Ui.echo(CommandUnmark.handle(tasks, argument, storage));
            break;
        case "delete":
            Ui.echo(CommandDelete.handle(tasks, argument, storage));
            break;
        case "todo":
        case "t":
            Ui.echo(CommandToDoTask.handle(tasks, argument, storage));
            break;
        case "deadline":
        case "d":
            Ui.echo(CommandDeadlineTask.handle(tasks, argument, storage));
            break;
        case "event":
        case "e":
            Ui.echo(CommandEventTask.handle(tasks, argument, storage));
            break;
        case "find":
        case "f":
            Ui.echo(CommandFind.handle(tasks, argument));
            break;
        default:
            Ui.echo(Constants.ERROR_INVALID_COMMAND);
            break;
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
            return CommandList.handle(tasks);
        case "mark":
            return CommandMark.handle(tasks, argument, storage);
        case "unmark":
            return CommandUnmark.handle(tasks, argument, storage);
        case "delete":
            return CommandDelete.handle(tasks, argument, storage);
        case "todo":
        case "t":
            return CommandToDoTask.handle(tasks, argument, storage);
        case "deadline":
        case "d":
            return CommandDeadlineTask.handle(tasks, argument, storage);
        case "event":
        case "e":
            return CommandEventTask.handle(tasks, argument, storage);
        case "find":
        case "f":
            return CommandFind.handle(tasks, argument);
        default:
            return Constants.ERROR_INVALID_COMMAND;
        }
    }
}
