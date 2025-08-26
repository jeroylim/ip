import java.io.*;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void saveTasks(TaskList taskList) {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (FullMarksBot.Task task : taskList.getTasks()) {
                writer.write(task.writtenTasks());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    public TaskList loadTasks() {
        TaskList taskList = new TaskList();
        File file = new File(filePath);
        if (!file.exists()) return taskList;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                String type = parts[0];
                boolean done = parts[1].equals("1");
                FullMarksBot.Task task = null;
                switch (type) {
                    case "T":
                        task = new FullMarksBot.Todo(parts[2]);
                        break;
                    case "D":
                        task = new FullMarksBot.Deadline(parts[2], parts[3]);
                        break;
                    case "E":
                        task = new FullMarksBot.Event(parts[2], parts[3], parts[4]);
                        break;
                }
                if (task != null && done) task.markDone();
                if (task != null) taskList.addTask(task);
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return taskList;
    }
}
