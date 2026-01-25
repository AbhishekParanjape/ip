import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task{
    private LocalDate dueDay;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");
    

    public Deadline(String description, String dueDay) {
        super(description);
        this.dueDay = LocalDate.parse(dueDay, INPUT_FORMAT);
    }

    public LocalDate getDueDay() {
        return dueDay;
    }

    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + dueDay;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " " + "(by: " + dueDay.format(OUTPUT_FORMAT) + ")";
    }
}