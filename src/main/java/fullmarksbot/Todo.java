package fullmarksbot;

/**
 * Represents a Todo task.
 */
public class Todo extends Task {
    /**
     * Constructs a Todo task with the given description.
     *
     * @param description Description of the todo.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String getStatusIcon() {
        return "[T] " + (isDone ? "[X] " : "[ ] ");
    }

    @Override
    public String writeTasks() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

}
