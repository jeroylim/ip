package fullmarksbot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Handles saving and loading tasks to and from persistent storage.
 */
public class Storage {
    private final String filePath;

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath Path to the file for storing tasks.
     */
    public Storage(String filePath) {
        assert filePath != null : "Storage filePath should not be null";
        this.filePath = filePath;
    }

    /**
     * Saves the given TaskList to the file.
     *
     * @param taskList List of tasks to save.
     */
    public void saveTasks(TaskList taskList) {
        assert taskList != null : "TaskList to save should not be null";
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (FullMarksBot.Task task : taskList.getTasks()) {
                assert task != null : "Task in saveTasks should not be null";
                writer.write(task.writeTasks());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the file and returns them as a TaskList.
     *
     * @return TaskList containing loaded tasks.
     */
    public TaskList loadTasks() {
        TaskList taskList = new TaskList();
        File file = new File(filePath);
        if (!file.exists()) {
            return taskList;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                assert parts.length >= 3 : "Saved task line should have at least 3 parts";
                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                FullMarksBot.Task task = null;
                switch (type) {
                case "T":
                    task = new FullMarksBot.Todo(parts[2]);
                    break;
                case "D":
                    assert parts.length >= 4 : "Deadline line should have 4 parts";
                    task = new FullMarksBot.Deadline(parts[2], parts[3]);
                    break;
                case "E":
                    assert parts.length >= 5 : "Event line should have 5 parts";
                    task = new FullMarksBot.Event(parts[2], parts[3], parts[4]);
                    break;
                }
                if (task != null && isDone) {
                    task.markDone();
                }
                if (task != null) {
                    taskList.addTask(task);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return taskList;
    }
}
