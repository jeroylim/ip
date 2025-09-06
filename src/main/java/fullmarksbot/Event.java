package fullmarksbot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task.
 */
public class Event extends Task {
    LocalDateTime startDate;
    LocalDateTime endDate;

    /**
     * Constructs an Event task with the given description, start date, and end date.
     *
     * @param description Description of the event.
     * @param startDate Start date in string format.
     * @param endDate End date in string format.
     */
    public Event(String description, String startDate, String endDate) throws FullMarksException {
        super(description);
        try {
            DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            this.startDate = LocalDateTime.parse(startDate, inputFormat);
            this.endDate = LocalDateTime.parse(endDate, inputFormat);
        } catch (Exception e) {
            throw new FullMarksException("Invalid date format. Please use yyyy-MM-ddTHH:mm");
        }
    }

    @Override
    public String getStatusIcon() {
        return "[E] " + (isDone ? "[X] " : "[ ] ");
    }

    @Override
    public String getDescription() {
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm a");
        return this.description + " (from: " + this.startDate.format(outputFormat)
                + " to: " + this.endDate.format(outputFormat) + ")";
    }

    @Override
    public String writeTasks() {
        return "E | " + (isDone ? "1" : "0") + " | "
                + description + " | " + startDate + " | " + endDate;
    }

}
