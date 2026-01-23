public class Deadline extends Task{
    public String dueDay;

    public Deadline(String description, String dueDay) {
        super(description);
        this.dueDay = dueDay;
    }

    public String getDueDay() {
        return "(by: " + this.dueDay + ")";
    }
    @Override
    public String toString() {
        return "[D]" + super.toString() + " " + getDueDay();
    }
}