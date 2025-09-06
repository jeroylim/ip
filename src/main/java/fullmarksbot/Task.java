package fullmarksbot;

/**
 * Represents a generic task.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task with the given description.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public abstract String getStatusIcon();

    public String getDescription() {
        return this.description;
    }

    public void markDone() {
        this.isDone = true;
    }

    public void markUndone() {
        this.isDone = false;
    }

    /**
     * Returns the string representation of the task for storage.
     *
     * @return Written format of the task.
     */
    public abstract String writeTasks();

}
