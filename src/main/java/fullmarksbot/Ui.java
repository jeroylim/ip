package fullmarksbot;

import java.util.Scanner;

/**
 * Handles user interaction for FullMarksBot.
 */
public class Ui {
    private final Scanner scanner;

    /**
     * Constructs an Ui object for user interaction.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message to the user.
     *
     * @param NAME Name of the bot.
     */
    public String showWelcomeString(String NAME) {
        assert NAME != null : "Bot name should not be null";
        return String.format("Hello, I'm %s, the bot that gives you full marks,"
                + " please write down what you want me to store!%n", NAME);
    }

    /**
     * Returns the task list as a formatted string for GUI display.
     *
     * @param taskList List of tasks.
     * @return Formatted string of tasks.
     */
    public String getTaskListString(TaskList taskList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < taskList.size(); i++) {
            Task t = taskList.getTask(i);
            sb.append((i + 1)).append(": ").append(t.getStatusIcon()).append(t.getDescription()).append("\n");
        }
        return sb.length() == 0 ? "No tasks yet!" : sb.toString().trim();
    }

    /**
     * Returns the found tasks as a formatted string for GUI display.
     *
     * @param foundTasks List of matching tasks.
     * @return Formatted string of found tasks.
     */
    public String getFoundTasksString(TaskList foundTasks) {
        if (foundTasks.size() == 0) {
            return "No matching tasks found.";
        }
        StringBuilder sb = new StringBuilder("Here are the related tasks:\n");
        for (int i = 0; i < foundTasks.size(); i++) {
            Task t = foundTasks.getTask(i);
            sb.append("     ").append(i + 1).append(".").append(t.getStatusIcon()).append(t.getDescription()).append("\n");
        }
        return sb.toString().trim();
    }
}