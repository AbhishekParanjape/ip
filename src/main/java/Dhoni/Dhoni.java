package dhoni;

import java.util.Scanner;

import dhoni.tasks.TaskList;
import dhoni.ui.Ui;

/**
 * Dhoni is a task management application that helps users keep track of their tasks.
 * It supports adding, marking, unmarking, deleting tasks, and displaying the task list.
 * This class provides both CLI and GUI interfaces for task management.
 */
public class Dhoni {

    private static TaskList tasks;
    private static Storage storage;
    private static Scanner scanner = new Scanner(System.in);
    private static final String filePath = "data/tasks.txt";

    /**
     * Constructs a Dhoni application instance.
     * Initializes storage and loads existing tasks from file.
     */
    public Dhoni() {
        storage = new Storage(filePath);

        try {
            tasks = new TaskList(storage.loadTasks());
        } catch (Exception e) {
            Ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Main method for command-line interface.
     * Starts the CLI version of the application.
     *
     * @param args Array of command line arguments (not used)
     * @throws Exception if there's an error during execution
     */
    public static void main(String[] args) {
        try {
            new Dhoni();
            Ui.echo(Constants.MSG_WELCOME);

            while (true) {
                String userInput = scanner.nextLine();
                if (userInput.trim().isEmpty()) {
                    Ui.echo(Constants.ERROR_EMPTY_INPUT);
                    continue;
                }

                String[] part = userInput.split("\\s+", 2);
                String command = part[0]; // e.g., "todo"
                String argument = (part.length > 1) ? part[1].trim() : "";

                // Use the same parser logic as main
                boolean isExit = Parser.execute(command, tasks, storage, scanner, argument, userInput);
                if (isExit) {
                    return;
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    /**
     * Generates a response for the user's message in GUI mode.
     *
     * @param userInput the user's input command
     * @return the response string to display to the user
     * @throws Exception if there's an error during processing
     */
    public String getResponse(String userInput) throws Exception {
        if (userInput == null) {
            return "";
        }

        String command = Parser.getCommand(userInput);
        String argument = Parser.getArgument(userInput);

        // Use the GUI executor that returns the response string instead of printing
        return Parser.executeGui(command, tasks, storage, argument, userInput);
    }

    /**
     * Gets the welcome message for GUI startup.
     *
     * @return the welcome message to display in GUI
     */
    public String getWelcomeMessage() {
        return Constants.MSG_WELCOME;
    }
}
