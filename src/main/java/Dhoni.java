import java.util.Scanner;
import java.util.ArrayList;

public class Dhoni {

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

    private static void handleDelete(ArrayList<Task> tasks, int index) {
        if (tasks.isEmpty()) {
            throw new IllegalArgumentException("No tasks to delete");
        }
        Task removed = tasks.remove(index);

        echo("\tNoted. I've removed this task:\n\t  "
                + removed
                + "\n\tNow you have " + tasks.size() + " tasks in the list.");
    }

    private static void hello() {
        System.out.println("\t" + line);
        System.out.println("\t" + "Hello! I'm " + name);
        System.out.println("\t" + "What can I do for you? I'm a cricket team captain that loves crushing t20s and tasks");
        System.out.println("\t" + line);
    }

    public static void main(String[] args) {

        ArrayList<Task> tasks = new ArrayList<>();
        hello();
        Scanner scanner = new Scanner(System.in);

        String userInput = scanner.nextLine();

        while (true) {
            if (userInput.equals("") || userInput.equals("blah")) {
                echo("Enter a valid task");
                userInput = scanner.nextLine();
                continue;
            }

            if (userInput.equals("bye")) {
                echo("Bye. Hope to see you again soon!");
                break;
            } else if (userInput.equals("list") && tasks.size() != 0) {
                if (tasks.isEmpty()) {
                    echo("\tHere are the tasks in your list:\n\t  (no tasks yet)");
                    scanner.nextLine();
                    continue;
                }
                StringBuilder sb = new StringBuilder();
                sb.append((1)).append(". ").append(tasks.get(0).toString());
                if (0 < tasks.size() - 1) {
                    sb.append("\n");
                }
                for (int i = 1; i < tasks.size();i++) {
                    sb.append("\t" ).append((i + 1)).append(". ").append(tasks.get(i).toString());
                    if (i < tasks.size() - 1) {
                        sb.append("\n");
                    }
                }
                echo(sb.toString());
            } else if (userInput.startsWith("mark")) {
                int index = Integer.parseInt(userInput.split(" ")[1]) - 1;
                tasks.get(index).completed();
                markCompleted("\t" + tasks.get(index).toString());
            } else if (userInput.startsWith("unmark")) {
                int index = Integer.parseInt(userInput.split(" ")[1]) - 1;
                tasks.get(index).unmark();
                unmarkCompleted("\t" + tasks.get(index).toString());
            } else if (userInput.startsWith("todo")) {
                // Error handling for empty description of todo
                if (userInput.length() <= 4) {
                    echo("Hey man, stop giving yourself empty tasks. You might not remember what you need to do.");
                    userInput = scanner.nextLine();
                    continue;
                }
                String desc = userInput.substring(5).trim(); // everything after "todo "
                tasks.add(new Todo(desc));
                

                echo("Got it. I've added this task:\n"
                        + "\t" + tasks.get(tasks.size() - 1)
                        + "\n\tNow you have " + tasks.size() + " tasks in the list.");
            } else if (userInput.startsWith("deadline")) {
                String[] parts = userInput.substring(9).trim().split("/by", 2);
                String desc = parts[0].trim();
                String by = parts[1].trim();
                tasks.add(new Deadline(desc, by));
        

                echo("Got it. I've added this task:\n"
                        + "\t" + tasks.get(tasks.size() - 1)
                        + "\n\tNow you have " + tasks.size() + " tasks in the list.");

            } else if (userInput.startsWith("event")) {
                // expected format: event <description> /from <start> /to <end>
                String[] parts = userInput.substring(6).trim().split("/from|/to", 3);
                String desc = parts[0].trim();
                String from = parts[1].trim();
                String to = parts[2].trim();
                tasks.add(new Event(desc, from, to));


                echo("Got it. I've added this task:\n"
                        + "\t" + tasks.get(tasks.size() - 1)
                        + "\n\tNow you have " + tasks.size() + " tasks in the list.");
            } else if (userInput.startsWith("delete")) {
                int index = Integer.parseInt(userInput.split(" ")[1]) - 1;
                handleDelete(tasks, index);
            } else {
                echo(userInput);
                tasks.add(new Task(userInput));

            }
            userInput = scanner.nextLine();
        }
        scanner.close();
    }
}