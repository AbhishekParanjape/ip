import java.util.Scanner;

public class Dhoni {

    private static final int MAX = 100;
    public static final String line = "-----------------------------------------";

    private static void echo(String text) {
        System.out.println("\t" + line);
        System.out.println("\t" + text);
        System.out.println("\t" + line);
    }

    private static void markCompleted(String text) {
        echo("Nice! I've marked this task as done:\n" + text);
    }

    private static void unmarkCompleted(String text) {
        echo("OK, I've marked this task as not done yet:\n" + text); 
    }

    public static void main(String[] args) {

        Task[] tasks = new Task[MAX];
        int size = 0;

        String name = "Dhoni";
        String line = "-----------------------------------------";
        System.out.println("\t" + line);
        System.out.println("\t" + "Hello! I'm " + name);
        System.out.println("\t" + "What can I do for you? I'm a cricket team captain that loves crushing t20s and tasks");
        System.out.println("\t" + line);

        Scanner scanner = new Scanner(System.in);

        String userInput = scanner.nextLine();

        while (true) {
            if (userInput.equals("bye")) {
                echo("\t" + "Bye. Hope to see you again soon!");
                break;
            } else if (userInput.equals("list") && size != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append((1)).append(". ").append(tasks[0].toString()).append("\n");
                for (int i = 1; i < size; i++) {
                    sb.append("\t" ).append((i + 1)).append(". ").append(tasks[i].toString());
                    if (i < size - 1) {
                        sb.append("\n");
                    }
                }
                echo(sb.toString());
            } else if (userInput.startsWith("mark")) {
                int index = Integer.parseInt(userInput.split(" ")[1]) - 1;
                tasks[index].completed();
                markCompleted("\t" + tasks[index].toString());
            } else if (userInput.startsWith("unmark")) {
                int index = Integer.parseInt(userInput.split(" ")[1]) - 1;
                tasks[index].unmark();
                unmarkCompleted("\t" + tasks[index].toString());
            } else {
                echo("\t" + userInput);
                tasks[size] = new Task(userInput);
                size++;
            }
            userInput = scanner.nextLine();
        }
        scanner.close();
    }
}


