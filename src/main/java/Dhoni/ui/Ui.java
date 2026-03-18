package dhoni.ui;

import dhoni.Constants;

/**
 * Ui class handles user interface operations.
 * This class provides methods to display messages and interact with the user.
 */
public class Ui {
    public static final String LINE = "-----------------------------------------";

    /**
     * Displays the welcome message to the user.
     */
    public static void hello() {
        echo(Constants.MSG_WELCOME);
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
        echo(Constants.MSG_LOADING_ERROR);
    }
}
