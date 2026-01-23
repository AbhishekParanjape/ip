import java.util.Scanner;

public class Dhoni {

    private static final int MAX = 100;
    public static final String line = "-----------------------------------------";
    public static final String name = "Dhoni";

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

    private static void hello() {
        System.out.println("\t" + line);
        System.out.println("\t" + "Hello! I'm " + name);
        System.out.println("\t" + "What can I do for you? I'm a cricket team captain that loves crushing t20s and tasks");
        System.out.println("\t" + line);
    }

    public static void main(String[] args) {

        Task[] tasks = new Task[MAX];
        int size = 0;
        hello();
        Scanner scanner = new Scanner(System.in);

        String userInput = scanner.nextLine();

        while (true) {
            if (userInput.equals("")) {
                echo("\tEnter a valid task\n\t");
                continue;
            }

            if (userInput.equals("bye")) {
                echo("Bye. Hope to see you again soon!");
                break;
            } else if (userInput.equals("list") && size != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append((1)).append(". ").append(tasks[0].toString());
                if (0 < size - 1) {
                    sb.append("\n");
                }
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
            } else if (userInput.startsWith("todo")) {
                String desc = userInput.substring(5).trim(); // everything after "todo "
                tasks[size] = new Todo(desc);
                size++;

                echo("Got it. I've added this task:\n"
                        + "\t" + tasks[size - 1]
                        + "\n\tNow you have " + size + " tasks in the list.");
            } else if (userInput.startsWith("deadline")) {
                String[] parts = userInput.substring(9).trim().split("/by", 2);
                String desc = parts[0].trim();
                String by = parts[1].trim();
                tasks[size] = new Deadline(desc, by);
                size++;

                echo("Got it. I've added this task:\n"
                        + "\t" + tasks[size - 1]
                        + "\n\tNow you have " + size + " tasks in the list.");

            } else if (userInput.startsWith("event")) {
                // expected format: event <description> /from <start> /to <end>
                String[] parts = userInput.substring(6).trim().split("/from|/to", 3);
                String desc = parts[0].trim();
                String from = parts[1].trim();
                String to = parts[2].trim();
                tasks[size] = new Event(desc, from, to);
                size++;

                echo("Got it. I've added this task:\n"
                        + "\t" + tasks[size - 1]
                        + "\n\tNow you have " + size + " tasks in the list.");
            } else {
                echo(userInput);
                tasks[size] = new Task(userInput);
                size++;
            }
            userInput = scanner.nextLine();
        }
        scanner.close();
    }
}