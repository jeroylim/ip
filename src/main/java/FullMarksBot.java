import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.regex.Pattern;

public class FullMarksBot {
    public static String NAME = "FullMarksBot";

    public static class Task {
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

    public static boolean containsExactWord(String input, String word) {
        String pattern = "(?i)\\b" + Pattern.quote(word) + "\\b";
        return Pattern.compile(pattern).matcher(input).find();
    }

    public static void main(String[] args) {
        System.out.printf("Hello, I'm %s," +
                "the bot that gives you full marks, " +
                "please write down what you want me to store!", NAME);

        Scanner scanner = new Scanner(System.in);
        String input;
        ArrayList<Task> tasks = new ArrayList<>();
        while (true) {
            input = scanner.nextLine();

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
                tasks.add(new Task(input));
                System.out.println("New Task: " + input);
            }

        }
    }
}
