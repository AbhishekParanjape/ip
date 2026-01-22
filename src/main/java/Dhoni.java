import java.util.Scanner;

public class Dhoni {
    private static final int MAX = 100;

    private static void echo(String text) {
        String line = "-----------------------------------------";
        System.out.println("\t" + line);
        System.out.println("\t" + text);
        System.out.println("\t" + line);
    }

    public static void main(String[] args) {

        String[] tasks = new String[MAX];
        int size = 0;

        String name = "Dhoni";
        String line = "-----------------------------------------";
        System.out.println("\t" + line);
        System.out.println("\t" + "Hello! I'm " + name);
        System.out.println("\t" + "What can I do for you?");
        System.out.println("\t" + line);

        Scanner scanner = new Scanner(System.in);

        String userInput = scanner.nextLine();

        while (true) {
            if (userInput.equals("bye")) {
                echo("\t" + "Bye. Hope to see you again soon!");
                break;
            } else if (userInput.equals("list") && size != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append((1)).append(". ").append(tasks[0]).append("\n");
                for (int i = 1; i < size; i++) {
                    sb.append("\t" ).append((i + 1)).append(". ").append(tasks[i]);
                    if (i < size - 1) {
                        sb.append("\n");
                    }
                }
                echo(sb.toString());
            } else {
                echo(userInput);
                tasks[size] = userInput;
                size++;
            }
            userInput = scanner.nextLine();
        }
        scanner.close();
    }
}

