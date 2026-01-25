package Dhoni;
public class Parser {
    /**
     * Extracts the command part from the user input.
     * @param input the full user input
     * @return the command part of the input
     */
    public static String getCommand(String input) {
        return input.split(" ", 2)[0].toLowerCase();
    }
    
    /**
     * Extracts the argument part from the user input.
     * @param input the full user input
     * @return the argument part of the input
     */
    public static String getArgument(String input) {
        String[] parts = input.split(" ", 2);
        return parts.length > 1 ? parts[1] : "";
    }
}
