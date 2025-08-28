package fullMarksBot;

import java.util.ArrayList;

/**
 * Manages a list of tasks and provides operations to modify and query the list.
 */
public class TaskList {
    private final ArrayList<FullMarksBot.Task> tasks = new ArrayList<>();

    /**
     * Adds a task to the list.
     *
     * @param task Task to be added.
     */
    public void addTask(FullMarksBot.Task task) {
        tasks.add(task);
    }

    /**
     * Deletes the task at the specified index.
     *
     * @param index Index of the task to delete.
     * @throws FullMarksBot.FullMarksException If the index is out of bounds.
     */
    public void deleteTask(int index) throws FullMarksBot.FullMarksException {
        if (index < 0 || index >= tasks.size()) {
            throw new FullMarksBot.FullMarksException("Task number " + (index + 1) + " does not exist.");
        }
        tasks.remove(index);
    }

    /**
     * Marks the task at the specified index as done.
     *
     * @param index Index of the task to mark as done.
     * @throws FullMarksBot.FullMarksException If the index is out of bounds.
     */
    public void markTask(int index) throws FullMarksBot.FullMarksException {
        if (index < 0 || index >= tasks.size()) {
            throw new FullMarksBot.FullMarksException("Task number " + (index + 1) + " does not exist.");
        }
        tasks.get(index).markDone();
    }

    /**
     * Marks the task at the specified index as not done.
     *
     * @param index Index of the task to unmark.
     * @throws FullMarksBot.FullMarksException If the index is out of bounds.
     */
    public void unmarkTask(int index) throws FullMarksBot.FullMarksException {
        if (index < 0 || index >= tasks.size()) {
            throw new FullMarksBot.FullMarksException("Task number " + (index + 1) + " does not exist.");
        }
        tasks.get(index).markUndone();
    }

    public ArrayList<FullMarksBot.Task> getTasks() {
        return tasks;
    }

    public int size() {
        return tasks.size();
    }

    public FullMarksBot.Task getTask(int index) {
        return tasks.get(index);
    }
}