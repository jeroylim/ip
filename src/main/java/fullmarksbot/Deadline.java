package fullmarksbot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task.
 */
public class Deadline extends Task {
    private final LocalDateTime endDate;

    /**
     * Constructs a Deadline task with the given description and end date.
     *
     * @param description Description of the deadline.
     * @param endDate End date in string format.
     */
    public Deadline(String description, String endDate) throws FullMarksException {
        super(description);
        try {
            DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            this.endDate = LocalDateTime.parse(endDate, inputFormat);
        } catch (Exception e) {
            throw new FullMarksException("Invalid date format. Please use yyyy-MM-ddTHH:mm");
        }
    }

    @Override
    public String getStatusIcon() {
        return "[D] " + (isDone ? "[X] " : "[ ] ");
    }

    @Override
    public String getDescription() {
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm a");
        return this.description + " (by: " + this.endDate.format(outputFormat) + ")";
    }

    @Override
    public String writeTasks() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + endDate;
    }

}
