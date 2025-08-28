package fullmarksbot;

import java.time.LocalDateTime;
import java.util.regex.Pattern;
import java.time.format.DateTimeFormatter;

/**
 * Main class for FullMarksBot, a bot for managing tasks.
 */
public class FullMarksBot {
    public static String NAME = "FullMarksBot";
    private static final String FILE_PATH = "./data/tasks.txt";

    /**
     * Represents a generic task.
     */
    public abstract static class Task {
        protected String description;
        protected boolean isDone;

        /**
         * Constructs a Task with the given description.
         *
         * @param description Description of the task.
         */
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

        /**
         * Returns the string representation of the task for storage.
         *
         * @return Written format of the task.
         */
        public abstract String writtenTasks();

    }

    /**
     * Represents a Todo task.
     */
    public static class Todo extends Task {
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
        public String writtenTasks() {
            return "T | " + (isDone ? "1" : "0") + " | " + description;
        }

    }

    /**
     * Represents a Deadline task.
     */
    public static class Deadline extends Task {
        private LocalDateTime endDate;

        /**
         * Constructs a Deadline task with the given description and end date.
         *
         * @param description Description of the deadline.
         * @param endDate End date in string format.
         */
        public Deadline(String description, String endDate) {
            super(description);
            DateTimeFormatter inputFormat1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            this.endDate = LocalDateTime.parse(endDate, inputFormat1);

        }

        @Override
        public String getStatusIcon() {
            return "[D] " + (isDone ? "[X] " : "[ ] ");
        }

        @Override
        public String getDescription() {
            DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm a");
            return this.description + " (by: " + this.endDate.format(outputFormat) + ")";
        }

        @Override
        public String writtenTasks() {
            return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + endDate;
        }

    }

    /**
     * Represents an Event task.
     */
    public static class Event extends Task {
        LocalDateTime startDate;
        LocalDateTime endDate;

        /**
         * Constructs an Event task with the given description, start date, and end date.
         *
         * @param description Description of the event.
         * @param startDate Start date in string format.
         * @param endDate End date in string format.
         */
        public Event(String description, String startDate, String endDate) {
            super(description);
            DateTimeFormatter inputFormat1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            this.startDate = LocalDateTime.parse(startDate, inputFormat1);
            this.endDate = LocalDateTime.parse(endDate, inputFormat1);
        }

        @Override
        public String getStatusIcon() {
            return "[E] " + (isDone ? "[X] " : "[ ] ");
        }

        @Override
        public String getDescription() {
            DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm a");
            return this.description + " (from: " + this.startDate.format(outputFormat)
                    + " to: " + this.endDate.format(outputFormat) + ")";
        }

        @Override
        public String writtenTasks() {
            return "E | " + (isDone ? "1" : "0") + " | "
                    + description + " | " + startDate + " | " + endDate;
        }

    }

    /**
     * Exception thrown for errors specific to FullMarksBot.
     */
    public static class FullMarksException extends Exception {
        /**
         * Constructs a FullMarksException with the given message.
         *
         * @param message Error message.
         */
        public FullMarksException(String message) {
            super(message);
        }
    }

    /**
     * Returns true if the input contains the exact word, case-insensitive.
     *
     * @param input Input string to search.
     * @param word Word to search for.
     * @return True if the word is found as a whole word, false otherwise.
     */
    public static boolean containsExactWord(String input, String word) {
        String pattern = "(?i)\\b" + Pattern.quote(word) + "\\b";
        return Pattern.compile(pattern).matcher(input).find();
    }

    /**
     * Runs the main loop of FullMarksBot.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage(FILE_PATH);
        TaskList tasks = storage.loadTasks();

        ui.showWelcome(NAME);

        while (true) {
            String input = ui.readCommand();
            try {
                String command = Parser.getCommandWord(input);
                switch (command) {
                case "list":
                    ui.showTaskList(tasks);
                    break;
                case "mark":
                    int markIdx = Parser.getTaskNumber(input);
                    tasks.markTask(markIdx);
                    ui.showMessage("Congrats! You completed this task!");
                    storage.saveTasks(tasks);
                    break;
                case "unmark":
                    int unmarkIdx = Parser.getTaskNumber(input);
                    tasks.unmarkTask(unmarkIdx);
                    ui.showMessage("Oh no! Let me unmark this...");
                    storage.saveTasks(tasks);
                    break;
                case "delete":
                    int delIdx = Parser.getTaskNumber(input);
                    tasks.deleteTask(delIdx);
                    ui.showMessage("Let's get this task out of here.");
                    storage.saveTasks(tasks);
                    break;
                case "bye":
                    ui.showMessage("bye bye for now!");
                    return;
                default:
                    String type = ui.ask("Is this a Todo, Deadline or Event Task?");
                    if (containsExactWord(type, "todo")) {
                        tasks.addTask(new Todo(input));
                        ui.showMessage("New Todo: " + input);
                    } else if (containsExactWord(type, "deadline")) {
                        String endDate = ui.ask("When should it be done by?"
                                + " Please format it like yyyy-MM-dd HH:mm");
                        if (endDate.trim().isEmpty()) {
                            throw new FullMarksException("Deadline date cannot be empty.");
                        }
                        tasks.addTask(new Deadline(input, endDate));
                        ui.showMessage("New Deadline: " + input);
                    } else if (containsExactWord(type, "event")) {
                        String startDate = ui.ask("When does this event start?"
                                + " Please format it like yyyy-MM-dd HH:mm");
                        String endDate = ui.ask("Now when does it end?"
                                + " Please format it like yyyy-MM-dd HH:mm");
                        if (startDate.trim().isEmpty() || endDate.trim().isEmpty()) {
                            throw new FullMarksException("Event dates cannot be empty.");
                        }
                        tasks.addTask(new Event(input, startDate, endDate));
                        ui.showMessage("New Event: " + input);
                    } else {
                        throw new FullMarksException("Invalid task type."
                                + " Please type 'todo', 'deadline' or 'event'.");
                    }
                    storage.saveTasks(tasks);
                    break;
                }
            } catch (FullMarksException e) {
                ui.showMessage(e.getMessage());
            } catch (Exception e) {
                ui.showMessage("Generic error: " + e.getMessage());
            }
        }
    }
}