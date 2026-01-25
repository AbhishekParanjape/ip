public class Deadline extends Task{
    private String dueDay;

    public Deadline(String description, String dueDay) {
        super(description);
        this.dueDay = dueDay;
    }

    public String getDueDay() {
        return "(by: " + this.dueDay + ")";
    }

    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + dueDay;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " " + getDueDay();
    }
}