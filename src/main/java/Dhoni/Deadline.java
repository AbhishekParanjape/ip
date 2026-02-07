package Dhoni;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task with a description and due date.
 * This class extends Task and adds a due date functionality.
 */
public class Deadline extends Task {
    private LocalDate dueDay;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");
    

    /**
     * Constructs a Deadline task with the given description and due date.
     * 
     * @param description the description of the deadline task
     * @param dueDay the due date in yyyy-MM-dd format
     */
    public Deadline(String description, String dueDay) {
        super(description);
        this.dueDay = LocalDate.parse(dueDay, INPUT_FORMAT);
    }

    /**
     * Gets the due date of the deadline task.
     * 
     * @return the due date as a LocalDate object
     */
    public LocalDate getDueDay() {
        return dueDay;
    }

    /**
     * Converts the deadline task to a file format string for saving.
     * 
     * @return string representation for file storage including due date
     */
    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + dueDay;
    }

    /**
     * Returns the string representation of the deadline task.
     * 
     * @return formatted string showing task status, description, and due date
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " " + "(by: " + dueDay.format(OUTPUT_FORMAT) + ")";
    }
}
