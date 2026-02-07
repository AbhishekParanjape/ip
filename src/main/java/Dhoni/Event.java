package Dhoni;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task with a description, start time, and end time.
 * This class extends Task and represents a task that occurs over a time period.
 */
public class Event extends Task {
    protected LocalDate fromTime;
    protected LocalDate toTime;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");
   

    /**
     * Constructs an Event task with the given description, start time, and end time.
     * 
     * @param description the description of the event task
     * @param from the start date in yyyy-MM-dd format
     * @param to the end date in yyyy-MM-dd format
     */
    public Event(String description, String from, String to) {
        super(description);
        this.fromTime = LocalDate.parse(from, INPUT_FORMAT);
        this.toTime = LocalDate.parse(to, INPUT_FORMAT);
    }

    /**
     * Gets the start date of the event.
     * 
     * @return the start date as a LocalDate object
     */
    public LocalDate getFrom() {
        return fromTime;
    }
    
    /**
     * Gets the end date of the event.
     * 
     * @return the end date as a LocalDate object
     */
    public LocalDate getTo() {
        return toTime;
    }

    /**
     * Converts the event task to a file format string for saving.
     * 
     * @return string representation for file storage including start and end dates
     */
    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + fromTime + " | " + toTime;
    }

    /**
     * Returns the string representation of the event task.
     * 
     * @return formatted string showing task status, description, and date range
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + fromTime.format(OUTPUT_FORMAT) + " to: " + toTime.format(OUTPUT_FORMAT) + ")";
    }
}
