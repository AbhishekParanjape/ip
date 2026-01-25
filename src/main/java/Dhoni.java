import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
/**
 * Dhoni is a task management application that helps users keep track of their tasks.
 * It supports adding, marking, unmarking, deleting tasks, and displaying the task list.
 */

public class Dhoni {

    public static final String LINE = "-----------------------------------------";
    public static final String NAME = "Dhoni";


    private final List<Task> tasks;

    public Dhoni() {
        this.tasks = Storage.loadTasks();
    }

    public static void main(String[] args) {
        Dhoni dhoni = new Dhoni();
        dhoni.run();
    }

    private void run() {
        hello();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.isEmpty()) {
                echo("Enter a valid command");
                continue;
            }

            String command = userInput.split(" ", 2)[0].toLowerCase();
            String argument = userInput.length() > command.length() ? userInput.substring(command.length() + 1) : "";

            switch (command) {
            case "bye":
                echo("Bye. Hope to see you again soon!");
                scanner.close();
                return;
            case "list":
                handleList(tasks);
                break;
            case "mark":
                handleMark(argument);
                Storage.saveTasks(tasks);
                break;
            case "unmark":
                handleUnmark(argument);
                Storage.saveTasks(tasks);
                break;
            case "delete":
                handleDelete(argument);
                Storage.saveTasks(tasks);
                break;
            case "todo":
                handleToDo(argument);
                Storage.saveTasks(tasks);
                break;
            case "deadline":
                handleDeadlineTask(argument);
                Storage.saveTasks(tasks);
                break;
            case "event":
                handleEventTask(argument);
                Storage.saveTasks(tasks);
                break;
            case "find":
                handleFindByDate(argument);
                break;
            default:
                tasks.add(new Todo(userInput));
                Storage.saveTasks(tasks);
                echo(userInput);
            }
        }
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
     private void handleUnmark(String argument) {
        try {
            int index = Integer.parseInt(argument.trim()) - 1;
            if (index < 0 || index >= tasks.size()) {
                echo("Invalid task number");
                return;
            }
            tasks.get(index).unmark();
            echo("OK, I've marked this task as not done yet:\n\t" + tasks.get(index));
        } catch (NumberFormatException e) {
            echo("Please provide a valid task number");
        }
    }

    /**
     * Prints confirmation that task has been marked as incomplete
     * @param text the task input as string
     */
    private void handleMark(String argument) {
        try {
            int index = Integer.parseInt(argument.trim()) - 1;
            if (index < 0 || index >= tasks.size()) {
                echo("Invalid task number");
                return;
            }
            tasks.get(index).completed();
            echo("Nice! I've marked this task as done:\n\t" + tasks.get(index));
        } catch (NumberFormatException e) {
            echo("Please provide a valid task number");
        }
    }

    private void handleDelete(String argument) {
        try {
            int index = Integer.parseInt(argument.trim()) - 1;
            if (index < 0 || index >= tasks.size()) {
                echo("Invalid task number");
                return;
            }
            Task removed = tasks.remove(index);
            echo("Noted. I've removed this task:\n\t" + removed + "\n\tNow you have " + tasks.size() + " tasks in the list.");
        } catch (NumberFormatException e) {
            echo("Please provide a valid task number");
        }
    }

    private static void handleList(List<Task> tasks) {
        if (tasks.isEmpty()) {
                echo("Here are the tasks in your list:\n\t(no tasks yet)");
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
    }

    /**
     * Handles the addition of a {@code Todo} task to the task list.
     * If description after todo is missing, the task is not added.
     *
     * @param tasks new Todo added to tasks
     * @param argument description everything after "todo"
     */
   private void handleToDo(String argument) {
        if (argument.trim().isEmpty()) {
            echo("Todo description cannot be empty");
            return;
        }
        Task todo = new Todo(argument.trim());
        tasks.add(todo);
        echo("Got it. I've added this task:\n\t" + todo + "\n\tNow you have " + tasks.size() + " tasks in the list.");
    }

    /**
     * Handles adding Deadline task to the task list.
     * If description after Deadline is missing, the task is not added.
     * 
     * @param tasks new Deadline task added to tasks
     * @param argument description of the deadline task including by date
     */
    private void handleDeadlineTask(String argument) {
        String[] parts = argument.split(" /by ");
        if (parts.length < 2 || parts[0].trim().isEmpty()) {
            echo("Deadline format: deadline <description> /by <date>");
            return;
        }
        Task deadline = new Deadline(parts[0].trim(), parts[1].trim());
        tasks.add(deadline);
        echo("Got it. I've added this task:\n\t" + deadline + "\n\tNow you have " + tasks.size() + " tasks in the list.");
    }

    /**
     * Handles adding event task to the task list.
     * @param tasks event task added to this list of tasks
     * @param argument description of event consisting of from and to date
     */
   private void handleEventTask(String argument) {
        String[] parts = argument.split(" /from ");
        if (parts.length < 2) {
            echo("Event format: event <description> /from <time> /to <time>");
            return;
        }
        
        String[] timeParts = parts[1].split(" /to ");
        if (timeParts.length < 2) {
            echo("Event format: event <description> /from <time> /to <time>");
            return;
        }
        
        Task event = new Event(parts[0].trim(), timeParts[0].trim(), timeParts[1].trim());
        tasks.add(event);
        echo("Got it. I've added this task:\n\t" + event + "\n\tNow you have " + tasks.size() + " tasks in the list.");
    }

    private void handleFindByDate(String argument) {
        if (argument.trim().isEmpty()) {
            echo("Usage: find yyyy-MM-dd");
            return;
        }
        
        try {
            LocalDate targetDate = LocalDate.parse(argument.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            StringBuilder sb = new StringBuilder("Tasks on " + targetDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":\n");
            
            boolean found = false;
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                if (task instanceof Deadline) {
                    if (((Deadline) task).getDueDay().equals(targetDate)) {
                        sb.append("\t").append((i + 1)).append(". ").append(task);
                        if (i < tasks.size() - 1) {
                            sb.append("\n");
                        }   
                        found = true;
                    }
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                    if (!event.getFrom().isAfter(targetDate) && !event.getTo().isBefore(targetDate)) {
                        sb.append("\t").append((i + 1)).append(". ").append(task);
                        if (i < tasks.size() - 1) {
                            sb.append("\n");
                        }   
                        found = true;
                    }
                }
            }
            
            if (!found) {
                sb.append("\tNo tasks on this date");
            }
            echo(sb.toString());
        } catch (Exception e) {
            echo("Invalid date format. Use yyyy-MM-dd");
        }
    }
}
