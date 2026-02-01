package Dhoni;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Dhoni is a task management application that helps users keep track of their tasks.
 * It supports adding, marking, unmarking, deleting tasks, and displaying the task list.
 */
public class Dhoni {
    
    private static TaskList tasks;
    private static Storage storage;
    private static Scanner scanner = new Scanner(System.in);
    private static final String filePath = "data/tasks.txt";
   
    public Dhoni() {
        storage = new Storage(filePath);

        try {
            tasks = new TaskList(storage.loadTasks());
        } catch (Exception e) {
            Ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public static void main(String[] args) throws Exception {
        new Dhoni();
        Ui.hello();

        while (true) {
            String userInput = scanner.nextLine();
            if (userInput.isEmpty()) {
                Ui.echo("\tEnter a valid task\n\t");
                continue;
            }

            if (userInput.equals("blah")) {
                Ui.echo("\tEnter a valid task\n\t");
                continue;
            }

            String[] part = userInput.split("\\s+", 2);
            String command = part[0]; // e.g., "todo"
            String argument = (part.length > 1) ? part[1].trim() : "";

            // Use the same parser logic as main
            boolean isExit = Parser.execute(command, tasks, storage, scanner, argument, userInput);
            if(isExit) {
                return;
            }
        }
    }

    /**
     * Generates a response for the user's message.
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
}
