import java.util.ArrayList;

public class TaskList {
    private final ArrayList<FullMarksBot.Task> tasks = new ArrayList<>();

    public void addTask(FullMarksBot.Task task) {
        tasks.add(task);
    }

    public void deleteTask(int index) throws FullMarksBot.FullMarksException {
        if (index < 0 || index >= tasks.size()) {
            throw new FullMarksBot.FullMarksException("Task number " + (index + 1) + " does not exist.");
        }
        tasks.remove(index);
    }

    public void markTask(int index) throws FullMarksBot.FullMarksException {
        if (index < 0 || index >= tasks.size()) {
            throw new FullMarksBot.FullMarksException("Task number " + (index + 1) + " does not exist.");
        }
        tasks.get(index).markDone();
    }

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