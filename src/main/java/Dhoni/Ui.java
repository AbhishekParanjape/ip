package Dhoni;

/**
 * Ui class handles user interface operations.
 * This class provides methods to display messages and interact with the user.
 */
public class Ui {
    public static final String LINE = "-----------------------------------------";
    public static final String NAME = "Dhoni";
    
    /**
     * Displays the welcome message to the user.
     */
    public static void hello() {
        System.out.println("\t" + LINE);
        System.out.println("\t" + "Hello! I'm " + NAME);
        System.out.println("\t" + "Welcome to the Dhoni Task Manager!");
        System.out.println("\t" + "I love crushing T20s and tasks - let's get this innings started!");
        System.out.println("\t" + "What can I do for you today?");
        System.out.println("\t" + LINE);
    }

    /**
     * Prints the given text in a formatted way with lines above and below.
     * 
     * @param text the text to print
     */
    public static void echo(String text) {
        System.out.println("\t" + LINE);
        System.out.println("\t" + text);
        System.out.println("\t" + LINE);
    }
    
    /**
     * Displays an error message when loading tasks from file fails.
     */
    public static void showLoadingError() {
        echo("Error loading tasks from file. Starting with an empty task list.");
    }
}
