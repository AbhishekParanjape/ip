package Dhoni;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Dhoni is a task management application that helps users keep track of their tasks.
 * It supports adding, marking, unmarking, deleting tasks, and displaying the task list.
 */
public class Dhoni {
    
    private TaskList tasks;
    private Ui ui;
    private Storage storage;
   
    public Dhoni(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);

        try {
            tasks = new TaskList(storage.loadTasks());
        } catch (Exception e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public static void main(String[] args) throws Exception {
        new Dhoni("ip/data/tasks.txt").run();
    }

    private void run() throws Exception {
        ui.hello();

        while (true) {
            String userInput = ui.readCommand();

            if (userInput.isEmpty()) {
                ui.echo("Enter a valid command");
                continue;
            }

            String command = Parser.getCommand(userInput);
            String argument = Parser.getArgument(userInput);
         
            switch (command) {
            case "bye":
                ui.echo("Bye. Hope to see you again soon!");
                ui.close();
                return;
            case "list":
                handleList(tasks);
                break;
            case "mark":
                handleMark(argument);
                storage.saveTasks(tasks);
                break;
            case "unmark":
                handleUnmark(argument);
                storage.saveTasks(tasks);
                break;
            case "delete":
                handleDelete(argument);
                storage.saveTasks(tasks);
                break;
            case "todo":
                handleToDo(argument);
                storage.saveTasks(tasks);
                break;
            case "deadline":
                handleDeadlineTask(argument);
                storage.saveTasks(tasks);
                break;
            case "event":
                handleEventTask(argument);
                storage.saveTasks(tasks);
                break;
            case "find":
                handleFindByDate(argument);
                break;
            default:
                tasks.addTask(new Todo(userInput));
                storage.saveTasks(tasks);
                ui.echo(userInput);
            }
        }
    }


    /**
     * Prints confirmation that task has been marked as complete
     * @param text the task input as string
     */
     private void handleUnmark(String argument) throws Exception {
        try {
            int index = Integer.parseInt(argument.trim()) - 1;
            if (index < 0 || index >= tasks.getSize()) {
                ui.echo("Invalid task number");
                return;
            }
            tasks.getTask(index).unmark();
            ui.echo("OK, I've marked this task as not done yet:\n\t" + tasks.getTask(index));
        } catch (NumberFormatException e) {
            ui.echo("Please provide a valid task number");
        }
    }

    /**
     * Prints confirmation that task has been marked as incomplete
     * @param text the task input as string
     */
    private void handleMark(String argument) throws Exception {
        try {
            int index = Integer.parseInt(argument.trim()) - 1;
            if (index < 0 || index >= tasks.getSize()) {
                ui.echo("Invalid task number");
                return;
            }
            tasks.getTask(index).completed();
            ui.echo("Nice! I've marked this task as done:\n\t" + tasks.getTask(index));
        } catch (NumberFormatException e) {
            ui.echo("Please provide a valid task number");
        }
    }

    private void handleDelete(String argument) throws Exception {
        try {
            int index = Integer.parseInt(argument.trim()) - 1;
            if (index < 0 || index >= tasks.getSize()) {
                ui.echo("Invalid task number");
                return;
            }
            Task removed = tasks.getTask(index);
            tasks.deleteTask(index);
            ui.echo("Noted. I've removed this task:\n\t" + removed + "\n\tNow you have " + tasks.getSize() + " tasks in the list.");
        } catch (NumberFormatException e) {
            ui.echo("Please provide a valid task number");
        }
    }

    private void handleList(TaskList tasks) throws Exception {
        if (tasks.isEmpty()) {
                ui.echo("Here are the tasks in your list:\n\t(no tasks yet)");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append((1)).append(". ").append(tasks.getTask(0).toString());
            if (0 < tasks.getSize() - 1) {
                sb.append("\n");
            }
            for (int i = 1; i < tasks.getSize();i++) {
                sb.append("\t" ).append((i + 1)).append(". ").append(tasks.getTask(i).toString());
                if (i < tasks.getSize() - 1) {
                    sb.append("\n");
                }
            }
            ui.echo(sb.toString());
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
            ui.echo("Todo description cannot be empty");
            return;
        }
        Task todo = new Todo(argument.trim());
        tasks.addTask(todo);
        ui.echo("Got it. I've added this task:\n\t" + todo + "\n\tNow you have " + tasks.getSize() + " tasks in the list.");
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
            ui.echo("Deadline format: deadline <description> /by <date>");
            return;
        }
        Task deadline = new Deadline(parts[0].trim(), parts[1].trim());
        tasks.addTask(deadline);
        ui.echo("Got it. I've added this task:\n\t" + deadline + "\n\tNow you have " + tasks.getSize() + " tasks in the list.");
    }

    /**
     * Handles adding event task to the task list.
     * @param tasks event task added to this list of tasks
     * @param argument description of event consisting of from and to date
     */
   private void handleEventTask(String argument) {
        String[] parts = argument.split(" /from ");
        if (parts.length < 2) {
            ui.echo("Event format: event <description> /from <time> /to <time>");
            return;
        }
        
        String[] timeParts = parts[1].split(" /to ");
        if (timeParts.length < 2) {
            ui.echo("Event format: event <description> /from <time> /to <time>");
            return;
        }
        
        Task event = new Event(parts[0].trim(), timeParts[0].trim(), timeParts[1].trim());
        tasks.addTask(event);
        ui.echo("Got it. I've added this task:\n\t" + event + "\n\tNow you have " + tasks.getSize() + " tasks in the list.");
    }

    private void handleFindByDate(String argument) {
        if (argument.trim().isEmpty()) {
            ui.echo("Usage: find yyyy-MM-dd");
            return;
        }
        
        try {
            LocalDate targetDate = LocalDate.parse(argument.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            StringBuilder sb = new StringBuilder("Tasks on " + targetDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":\n");
            
            boolean found = false;
            for (int i = 0; i < tasks.getSize(); i++) {
                Task task = tasks.getTask(i);
                if (task instanceof Deadline) {
                    if (((Deadline) task).getDueDay().equals(targetDate)) {
                        sb.append("\t").append((i + 1)).append(". ").append(task);
                        if (i < tasks.getSize() - 1) {
                            sb.append("\n");
                        }   
                        found = true;
                    }
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                    if (!event.getFrom().isAfter(targetDate) && !event.getTo().isBefore(targetDate)) {
                        sb.append("\t").append((i + 1)).append(". ").append(task);
                        if (i < tasks.getSize() - 1) {
                            sb.append("\n");
                        }   
                        found = true;
                    }
                }
            }
            
            if (!found) {
                sb.append("\tNo tasks on this date");
            }
            ui.echo(sb.toString());
        } catch (Exception e) {
            ui.echo("Invalid date format. Use yyyy-MM-dd");
        }
    }
}
