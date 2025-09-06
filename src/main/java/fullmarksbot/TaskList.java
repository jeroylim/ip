package fullmarksbot;

import java.util.ArrayList;

/**
 * Manages a list of tasks and provides operations to modify and query the list.
 */
public class TaskList {
    private final ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Adds a task to the list.
     *
     * @param task Task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes the task at the specified index.
     *
     * @param index Index of the task to delete.
     * @throws FullMarksException If the index is out of bounds.
     */
    public void deleteTask(int index) throws FullMarksException {
        if (index < 0 || index >= tasks.size()) {
            throw new FullMarksException("Task number " + (index + 1) + " does not exist.");
        }
        tasks.remove(index);
    }

    /**
     * Marks the task at the specified index as done.
     *
     * @param index Index of the task to mark as done.
     * @throws FullMarksException If the index is out of bounds.
     */
    public void markTask(int index) throws FullMarksException {
        if (index < 0 || index >= tasks.size()) {
            throw new FullMarksException("Task number " + (index + 1)
                    + " does not exist.");
        }
        tasks.get(index).markDone();
    }

    /**
     * Marks the task at the specified index as not done.
     *
     * @param index Index of the task to unmark.
     * @throws FullMarksException If the index is out of bounds.
     */
    public void unmarkTask(int index) throws FullMarksException {
        if (index < 0 || index >= tasks.size()) {
            throw new FullMarksException("Task number " + (index + 1)
                    + " does not exist.");
        }
        tasks.get(index).markUndone();
    }

    /**
     * Returns a list of tasks whose description contains the given keyword (case-insensitive).
     *
     * @param keyword Keyword to search for in task descriptions.
     * @return ArrayList of matching tasks.
     */
    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> matches = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(lowerKeyword)) {
                matches.add(task);
            }
        }
        return matches;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int size() {
        return tasks.size();
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }
}