public class Parser {
    public static String getCommand(String input) {
        return input.split(" ", 2)[0].toLowerCase();
    }
    
    public static String getArgument(String input) {
        String[] parts = input.split(" ", 2);
        return parts.length > 1 ? parts[1] : "";
    }
}
