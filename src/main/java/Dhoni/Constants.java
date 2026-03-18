package dhoni;

/**
 * Constants class containing all magic literals used throughout the application.
 * This improves code maintainability and reduces duplication.
 */
public class Constants {
    // Command constants
    public static final String CMD_BYE = "bye";
    public static final String CMD_LIST = "list";
    public static final String CMD_MARK = "mark";
    public static final String CMD_UNMARK = "unmark";
    public static final String CMD_DELETE = "delete";
    public static final String CMD_TODO = "todo";
    public static final String CMD_TODO_SHORT = "t";
    public static final String CMD_DEADLINE = "deadline";
    public static final String CMD_DEADLINE_SHORT = "d";
    public static final String CMD_EVENT = "event";
    public static final String CMD_EVENT_SHORT = "e";
    public static final String CMD_FIND = "find";
    public static final String CMD_FIND_SHORT = "f";

    // Task type constants for file format
    public static final String TASK_TYPE_TODO = "T";
    public static final String TASK_TYPE_DEADLINE = "D";
    public static final String TASK_TYPE_EVENT = "E";
    public static final String TASK_TYPE_GENERIC = "X";

    // File format constants
    public static final String FILE_SEPARATOR = " | ";
    public static final String DONE_STATUS_TRUE = "1";
    public static final String DONE_STATUS_FALSE = "0";

    // Date format
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    // Error messages
    public static final String ERROR_INVALID_COMMAND = "I don't understand that command. "
        + "Please use: todo, deadline, event, mark, unmark, delete, list, find, or bye.";
    public static final String ERROR_EMPTY_INPUT = "Please enter a valid command.";
    public static final String ERROR_PAST_DATE = "Cannot set deadline to a past date. Please use a future date.";

    // UI messages
    public static final String MSG_WELCOME = "Hello! I'm Dhoni\nWelcome to the Dhoni Task Manager!\n"
        + "I love crushing T20s and tasks - let's get this innings started!\nWhat can I do for you today?";
    public static final String MSG_BYE = "Bye. Hope to see you again soon!";
    public static final String MSG_LOADING_ERROR = "Error loading tasks from file. Starting with an empty task list.";

    // Task validation messages
    public static final String ERROR_DEADLINE_FORMAT = "Deadline format: deadline <description> /by <date>. "
        + "Missing '/by' parameter.";
    public static final String ERROR_DEADLINE_MULTIPLE_BY = "Deadline format: deadline <description> /by <date>. "
        + "Multiple '/by' parameters detected.";
    public static final String ERROR_DEADLINE_EMPTY_DESC = "Deadline description cannot be empty. "
        + "Usage: deadline <description> /by <date>";
    public static final String ERROR_DEADLINE_EMPTY_DATE = "Deadline date cannot be empty. "
        + "Usage: deadline <description> /by <date>";
    public static final String ERROR_DEADLINE_INVALID_DATE = "Invalid date format: '%s'. Expected yyyy-MM-dd format "
        + "(e.g., 2023-12-25)";
    public static final String ERROR_DEADLINE_DUPLICATE = "Duplicate deadline detected: '%s' due '%s'."
        + " Task already exists.";
    // Task success messages
    public static final String MSG_TASK_ADDED = "Got it. I've added this task:\n\t%s"
        + "\n\tNow you have %d tasks in the list.";
    public static final String MSG_DEADLINE_ADDED = "Got it. I've added this task:\n\t%s"
        + "\n\tNow you have %d tasks in the list.\n\tDeadline set - time to finish strong!";
    // Event task constants
    public static final String ERROR_EVENT_FORMAT = "Event format: event <description> /from <time> /to <time>. "
        + "Missing '/from' parameter.";
    public static final String ERROR_EVENT_MULTIPLE_FROM = "Event format: event <description> /from <time> /to <time>. "
        + "Multiple '/from' parameters detected.";
    public static final String ERROR_EVENT_FORMAT_TO = "Event format: event <description> /from <time> /to <time>. "
        + "Missing '/to' parameter.";
    public static final String ERROR_EVENT_MULTIPLE_TO = "Event format: event <description> /from <time> /to <time>. "
        + "Multiple '/to' parameters detected.";
    public static final String ERROR_EVENT_EMPTY_DESC = "Event description cannot be empty. "
        + "Usage: event <description> /from <time> /to <time>";
    public static final String ERROR_EVENT_EMPTY_FROM = "Event start time cannot be empty. "
        + "Usage: event <description> /from <time> /to <time>";
    public static final String ERROR_EVENT_EMPTY_TO = "Event end time cannot be empty. "
        + "Usage: event <description> /from <time> /to <time>";
    public static final String ERROR_EVENT_INVALID_DATE = "Invalid date format. Expected yyyy-MM-dd format "
        + "(e.g., 2023-12-25)";
    public static final String ERROR_EVENT_PAST_DATE = "Cannot set event to a past date. Please use a future date.";
    public static final String ERROR_EVENT_DATE_RANGE = "Invalid date range: start date cannot be after end date";
    public static final String ERROR_EVENT_SAME_DATE = "Invalid date range: start and end dates cannot be the same";
    public static final String ERROR_EVENT_DUPLICATE = "Duplicate event detected: '%s' from '%s' to '%s'. "
        + "Task already exists.";
    public static final String MSG_EVENT_ADDED = "Got it. I've added this task:\n\t%s"
        + "\n\tNow you have %d tasks in the list.\n\tEvent scheduled - let's make it count!";
}
