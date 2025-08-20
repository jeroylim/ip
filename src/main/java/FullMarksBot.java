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
            return "[T] " + (isDone ? "[X] " : "[ ] "); // mark done task with X
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
            return "[D] " + (isDone ? "[X] " : "[ ] "); // mark done task with X
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
            return "[E] " + (isDone ? "[X] " : "[ ] "); // mark done task with X
        }

        @Override
        public String getDescription() {
            return this.description + " (from: " + this.startDate + " to: " + this.endDate + ")";
        }
    }

    public static boolean containsExactWord(String input, String word) {
        String pattern = "(?i)\\b" + Pattern.quote(word) + "\\b";
        return Pattern.compile(pattern).matcher(input).find();
    }

    public static void main(String[] args) {
        System.out.printf("Hello, I'm %s," +
                "the bot that gives you full marks, " +
                "please write down what you want me to store!", NAME);

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        while (true) {
            String input = scanner.nextLine();

            if (containsExactWord(input, "list")) {
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ": " + tasks.get(i).getStatusIcon() + tasks.get(i).getDescription());
                }
            } else if (containsExactWord(input, "mark")) {
                String[] parts = input.split(" ");
                int taskNumber = Integer.parseInt(parts[1]); // parts[1] is "2" from "mark 2"
                Task selectedTask = tasks.get(taskNumber - 1);
                selectedTask.markDone();
                System.out.println("Congrats! You completed this task!");
            } else if (containsExactWord(input, "unmark")) {
                String[] parts = input.split(" ");
                int taskNumber = Integer.parseInt(parts[1]); // parts[1] is "2" from "mark 2"
                Task selectedTask = tasks.get(taskNumber - 1);
                selectedTask.markUndone();
                System.out.println("Oh no! Let me unmark this...");
            } else if (containsExactWord(input, "bye")) {
                System.out.println("bye bye for now!");
                break;
            } else {
                System.out.println("Is this a Todo, Deadline or Event Task?");
                String type = scanner.nextLine();
                if (containsExactWord(type, "todo")) {
                    tasks.add(new Todo(input));
                    System.out.println("New Todo: " + input);
                } else if (containsExactWord(type, "deadline")) {
                    System.out.println("When should it be done by?");
                    String endDate = scanner.nextLine();
                    tasks.add(new Deadline(input, endDate));
                    System.out.println("New Deadline: " + input);
                } else {
                    System.out.println("When does this event start?");
                    String startDate = scanner.nextLine();
                    System.out.println("Now when does it end?");
                    String endDate = scanner.nextLine();
                    tasks.add(new Event(input, startDate, endDate));
                    System.out.println("New Event: " + input);
                }
            }
        }
    }
}
