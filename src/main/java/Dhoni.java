import java.util.Scanner;
import java.util.ArrayList;

public class Dhoni {

    public static final String LINE = "-----------------------------------------";
    public static final String NAME = "Dhoni";

    /**
     * Prints message with lines for seperation
     * @param text the message to be displayed
     */
    private static void echo(String text) {
        System.out.println("\t" + LINE);
        System.out.println("\t" + text);
        System.out.println("\t" + LINE);
    }

    /**
     * Prints confirmation that task has been marked as complete
     * @param text the task input as string
     */
    private static void markCompleted(String text) {
        echo("Nice! I've marked this task as done:\n" + text);
    }

    /**
     * Prints confirmation that task has been marked as incomplete
     * @param text the task input as string
     */
    private static void unmarkCompleted(String text) {
        echo("OK, I've marked this task as not done yet:\n" + text); 
    }

    private static void handleDelete(ArrayList<Task> tasks, int index) {
        if (tasks.isEmpty()) {
            throw new IllegalArgumentException("No tasks to delete");
        }
        Task removed = tasks.remove(index);

        echo("Noted. I've removed this task:\n\t"
                + removed
                + "\n\tNow you have " + tasks.size() + " tasks in the list.");
    }

    /**
     * Prints a welcome message when program starts
     */
    private static void hello() {
        System.out.println("\t" + LINE);
        System.out.println("\t" + "Hello! I'm " + NAME);
        System.out.println("\t" + "What can I do for you? I'm a cricket team captain that loves crushing t20s and tasks");
        System.out.println("\t" + LINE);
    }

    /**
     * Handles the addition of a {@code Todo} task to the task list.
     * If description after todo is missing, the task is not added.
     *
     * @param tasks new Todo added to tasks
     * @param argument description everything after "todo"
     */
    public static void handleToDo(ArrayList<Task> tasks, String argument) {
        if (argument == null || argument.isBlank()) {
            echo("Hey man, stop giving yourself empty tasks. You might not remember what you need to do.");
            return;
        }
        String desc = argument.trim(); 
        tasks.add(new Todo(desc));

        echo("Got it. I've added this task:\n\t"
                + tasks.get(tasks.size() - 1)
                + "\n\tNow you have " + tasks.size() + " tasks in the list.");
    }

    /**
     * Handles adding Deadline task to the task list.
     * If description after Deadline is missing, the task is not added.
     * 
     * @param tasks new Deadline task added to tasks
     * @param argument description of the deadline task including by date
     */
    public static void handleDeadlineTask(ArrayList<Task> tasks, String argument) {
        if (argument == null || argument.isBlank()) {
            echo("Hey man, stop giving yourself empty tasks. You might not remember what you need to do.");
            return;
        }
        String[] parts = argument.split("/by", 2);
        String desc = parts[0].trim();
        String by = parts[1].trim();
        tasks.add(new Deadline(desc, by));

        echo("Got it. I've added this task:\n"
                + "\t" + tasks.get(tasks.size() - 1)
                + "\n\tNow you have " + tasks.size() + " tasks in the list.");
    }

    /**
     * Handles adding event task to the task list.
     * @param tasks event task added to this list of tasks
     * @param argument description of event consisting of from and to date
     */
    public static void handleEventTask(ArrayList<Task> tasks, String argument) {
        String[] parts = argument.split("/from|/to", 3);
        String desc = parts[0].trim();
        String from = parts[1].trim();
        String to = parts[2].trim();
        tasks.add(new Event(desc, from, to));
        int numberOfTasks = tasks.size();

        echo("Got it. I've added this task:\n\t"
                + tasks.get(numberOfTasks - 1)
                + "\n\tNow you have " + numberOfTasks + " tasks in the list.");
    }

    public static void main(String[] args) {
        hello();

        ArrayList<Task> tasks = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        String userInput = scanner.nextLine();

        while (true) {
            if (userInput.equals("") || userInput.equals("blah")) {
                echo("Enter a valid task");
                userInput = scanner.nextLine();
                continue;
            }

            String[] parts = userInput.split("\\s+", 2);
            String taskType = parts[0];                         // e.g., "Deadline"
            String taskArgs = (parts.length > 1) ? parts[1].trim() : ""; // e.g., "Train cricket"

            switch (taskType) {
            case "bye": {
                echo("Bye. Hope to see you again soon!");
                scanner.close();
                return;
            }
            case "list": {
                if (tasks.isEmpty()) {
                    echo("Here are the tasks in your list:\n\t  (no tasks yet)");
                    scanner.nextLine();
                } else {
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
                }
                break;
            }
            case "mark": {
                int index = Integer.parseInt(userInput.split(" ")[1]) - 1;
                tasks.get(index).completed();
                markCompleted("\t" + tasks.get(index).toString());
                break;
            }
            case "unmark": {
                int index = Integer.parseInt(userInput.split(" ")[1]) - 1;
                tasks.get(index).unmark();
                unmarkCompleted("\t" + tasks.get(index).toString());
                break;
            }
            case "todo": {
                // Error handling for empty description
                if (userInput.length() == 4) {
                    echo("Hey man, stop giving yourself empty tasks. You might not remember what you need to do.");
                    continue;
                }
                handleToDo(tasks, taskArgs);
                break;
            }
            case "deadline": {
                handleDeadlineTask(tasks, taskArgs);
                break;
            }
            case "event": {
                handleEventTask(tasks, taskArgs);
                break;
            }
            case "delete": {
                int index = Integer.parseInt(userInput.split(" ")[1]) - 1;
                handleDelete(tasks, index);
                break;
            }
            default: {
                tasks.add(new Task(userInput));
                echo(userInput);
                break;
            }
            }
            userInput = scanner.nextLine();   
        }
    }
}