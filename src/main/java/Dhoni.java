import java.util.Scanner;

public class Dhoni {
    private static void echo(String text) {
        String line = "-----------------------------------------";
        System.out.println("\t" + line);
        System.out.println("\t" + text);
        System.out.println("\t" + line);
    }

    public static void main(String[] args) {
        String name = "Dhoni";
        String line = "-----------------------------------------";
        System.out.println("\t" + line);
        System.out.println("\t" + "Hello! I'm " + name);
        System.out.println("\t" + "What can I do for you?");
        System.out.println("\t" + line);

        Scanner scanner = new Scanner(System.in);

        String userInput = scanner.nextLine();

        while (!userInput.equals("bye")) {
            echo(userInput);
            userInput = scanner.nextLine();
        }
        scanner.close();
        System.out.println("\t" + line);
        System.out.println("\t" + "Bye. Hope to see you again soon!");
        System.out.println("\t" + line);

    }
}

