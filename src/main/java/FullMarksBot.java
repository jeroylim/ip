import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class FullMarksBot {
    public static String NAME = "FullMarksBot";
    private static final String FILE_PATH = "./data/tasks.txt";

    public abstract static class Task {
        protected String description;
        protected boolean isDone;

        public Task(String description) {
            this.description = description;
            this.isDone = false;
        }

        public String getStatusIcon() {
            return (isDone ? "[X] " : "[ ] "); // mark done task with X
        }

        public String getDescription() {
            return this.description;
        }

        public void markDone() {
            this.isDone = true;
        }

        public void markUndone() {
            this.isDone = false;
        }

        public abstract String writtenTasks();

    }

    public static class Todo extends Task {
        public Todo(String description) {
            super(description);
        }

        @Override
        public String getStatusIcon() {
            return "[T] " + (isDone ? "[X] " : "[ ] ");
        }

        @Override
        public String writtenTasks() {
            return "T | " + (isDone ? "1" : "0") + " | " + description;
        }

    }

    public static class Deadline extends Task {
        String endDate;

        public Deadline(String description, String endDate) {
            super(description);
            this.endDate = endDate;
        }

        @Override
        public String getStatusIcon() {
            return "[D] " + (isDone ? "[X] " : "[ ] ");
        }

        @Override
        public String getDescription() {
            return this.description + " (by: " + this.endDate + ")";
        }

        @Override
        public String writtenTasks() {
            return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + endDate;
        }

    }

    public static class Event extends Task {
        String startDate;
        String endDate;

        public Event(String description, String startDate, String endDate) {
            super(description);
            this.startDate = startDate;
            this.endDate = endDate;
        }

        @Override
        public String getStatusIcon() {
            return "[E] " + (isDone ? "[X] " : "[ ] ");
        }

        @Override
        public String getDescription() {
            return this.description + " (from: " + this.startDate + " to: " + this.endDate + ")";
        }

        @Override
        public String writtenTasks() {
            return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + startDate + " | " + endDate;
        }

    }

    public static class FullMarksException extends Exception {
        public FullMarksException(String message) {
            super(message);
        }
    }

    public static boolean containsExactWord(String input, String word) {
        String pattern = "(?i)\\b" + Pattern.quote(word) + "\\b";
        return Pattern.compile(pattern).matcher(input).find();
    }

    private static void markTask(String input, ArrayList<Task> tasks) throws FullMarksException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new FullMarksException("Please specify a task number after 'mark'.");
        }
        int taskNumber;
        try {
            taskNumber = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new FullMarksException("Invalid task number. Please enter a number.");
        }
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new FullMarksException("Task number " + taskNumber + " does not exist.");
        }
        tasks.get(taskNumber - 1).markDone();
        System.out.println("Congrats! You completed this task!");
        saveTasks(tasks);
    }

    private static void unmarkTask(String input, ArrayList<Task> tasks) throws FullMarksException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new FullMarksException("Please specify a task number after 'unmark'.");
        }
        int taskNumber;
        try {
            taskNumber = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new FullMarksException("Invalid task number. Please enter a number.");
        }
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new FullMarksException("Task number " + taskNumber + " does not exist.");
        }
        tasks.get(taskNumber - 1).markUndone();
        System.out.println("Oh no! Let me unmark this...");
        saveTasks(tasks);
    }

    private static void deleteTask(String input, ArrayList<Task> tasks) throws FullMarksException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new FullMarksException("Please specify a task number after 'delete'.");
        }
        int taskNumber;
        try {
            taskNumber = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new FullMarksException("Invalid task number. Please enter a number.");
        }
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new FullMarksException("Task number " + taskNumber + " does not exist.");
        }
        tasks.remove(taskNumber - 1);
        System.out.println("Let's get this task out of here.");
        saveTasks(tasks);
    }

    private static void addTask(String input, ArrayList<Task> tasks, Scanner scanner) throws FullMarksException {
        System.out.println("Is this a Todo, Deadline or Event Task?");
        String type = scanner.nextLine();

        if (containsExactWord(type, "todo")) {
            tasks.add(new Todo(input));
            System.out.println("New Todo: " + input);
        } else if (containsExactWord(type, "deadline")) {
            System.out.println("When should it be done by?");
            String endDate = scanner.nextLine();
            if (endDate.trim().isEmpty()) {
                throw new FullMarksException("Deadline date cannot be empty.");
            }
            tasks.add(new Deadline(input, endDate));
            System.out.println("New Deadline: " + input);
        } else if (containsExactWord(type, "event")) {
            System.out.println("When does this event start?");
            String startDate = scanner.nextLine();
            System.out.println("Now when does it end?");
            String endDate = scanner.nextLine();
            if (startDate.trim().isEmpty() || endDate.trim().isEmpty()) {
                throw new FullMarksException("Event dates cannot be empty.");
            }
            tasks.add(new Event(input, startDate, endDate));
            System.out.println("New Event: " + input);
        } else {
            throw new FullMarksException("Invalid task type. Please type 'todo', 'deadline' or 'event'.");
        }
        saveTasks(tasks);
    }

    private static void saveTasks(ArrayList<Task> tasks) {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Task task : tasks) {
                writer.write(task.writtenTasks());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    private static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return tasks;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                String type = parts[0];
                boolean done = parts[1].equals("1");
                Task task = null;
                switch (type) {
                    case "T":
                        task = new Todo(parts[2]);
                        break;
                    case "D":
                        task = new Deadline(parts[2], parts[3]);
                        break;
                    case "E":
                        task = new Event(parts[2], parts[3], parts[4]);
                        break;
                }
                if (task != null && done) task.markDone();
                if (task != null) tasks.add(task);
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }



    public static void main(String[] args) {
        System.out.printf("Hello, I'm %s, " +
                "the bot that gives you full marks, " +
                "please write down what you want me to store!%n", NAME);

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = loadTasks();

        while (true) {
            String input = scanner.nextLine();
            try {
                if (containsExactWord(input, "list")) {
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ": " + tasks.get(i).getStatusIcon() + tasks.get(i).getDescription());
                    }
                } else if (containsExactWord(input, "mark")) {
                    markTask(input, tasks);
                } else if (containsExactWord(input, "unmark")) {
                    unmarkTask(input, tasks);
                } else if (containsExactWord(input, "delete")) {
                    deleteTask(input, tasks);
                } else if (containsExactWord(input, "bye")) {
                    System.out.println("bye bye for now!");
                    break;
                } else {
                    addTask(input, tasks, scanner);
                }
            } catch (FullMarksException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Generic error: " + e.getMessage());
            }
        }
    }
}