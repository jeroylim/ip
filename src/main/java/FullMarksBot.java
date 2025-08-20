import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class FullMarksBot {
    public static String NAME = "FullMarksBot";

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
    }

    public static class Todo extends Task {
        public Todo(String description) {
            super(description);
        }

        @Override
        public String getStatusIcon() {
            return "[T] " + (isDone ? "[X] " : "[ ] ");
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
    }

    public static void main(String[] args) {
        System.out.printf("Hello, I'm %s, " +
                "the bot that gives you full marks, " +
                "please write down what you want me to store!%n", NAME);

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

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
