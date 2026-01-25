package Dhoni;
import java.util.Scanner;

public class Ui {
    public static final String LINE = "-----------------------------------------";
    public static final String NAME = "Dhoni";
    private Scanner scanner;
    
    public Ui() {
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Displays the welcome message to the user.
     */
    public void hello() {
        System.out.println("\t" + LINE);
        System.out.println("\t" + "Hello! I'm " + NAME);
        System.out.println("\t" + "What can I do for you? I'm a cricket team captain that loves crushing t20s and tasks");
        System.out.println("\t" + LINE);
    }

    /**
     * Prints the given text in a formatted way with lines above and below.
     * @param text the text to print
     */
    public void echo(String text) {
        System.out.println("\t" + LINE);
        System.out.println("\t" + text);
        System.out.println("\t" + LINE);
    }
    
    public void showLoadingError() {
        echo("Error loading tasks from file. Starting with an empty task list.");
    }
    
    public String readCommand() {
        return scanner.nextLine().trim();
    }
    
    public void close() {
        scanner.close();
    }
}