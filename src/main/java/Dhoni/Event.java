package Dhoni;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task with a description, start time, and end time.
 */
public class Event extends Task {
    protected LocalDate fromTime;
    protected LocalDate toTime;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");
   

    public Event(String description, String from, String to) {
        super(description);
        this.fromTime = LocalDate.parse(from, INPUT_FORMAT);
        this.toTime = LocalDate.parse(to, INPUT_FORMAT);
    }

    public LocalDate getFrom() {
        return fromTime;
    }
    
    public LocalDate getTo() {
        return toTime;
    }

    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + fromTime + " | " + toTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + fromTime.format(OUTPUT_FORMAT) + " to: " + toTime.format(OUTPUT_FORMAT) + ")";
    }
}
