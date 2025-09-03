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
    public void showWelcome(String NAME) {
        System.out.printf("Hello, I'm %s, the bot that gives you full marks,"
                + " please write down what you want me to store!%n", NAME);
    }

    /**
     * Reads the next command from the user.
     *
     * @return User input string.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays the list of tasks to the user.
     *
     * @param taskList List of tasks to display.
     */
    public void showTaskList(TaskList taskList) {
        for (int i = 0; i < taskList.size(); i++) {
            FullMarksBot.Task t = taskList.getTask(i);
            System.out.println((i + 1) + ": " + t.getStatusIcon()
                    + t.getDescription());
        }
    }

    // Helper for GUI: get task list string
    public String getTaskListString(TaskList taskList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < taskList.size(); i++) {
            FullMarksBot.Task t = taskList.getTask(i);
            sb.append((i + 1)).append(": ").append(t.getStatusIcon()).append(t.getDescription()).append("\n");
        }
        return sb.length() == 0 ? "No tasks yet!" : sb.toString().trim();
    }

    /**
     * Displays a message to the user.
     *
     * @param msg Message to display.
     */
    public void showMessage(String msg) {
        System.out.println(msg);
    }

    /**
     * Prompts the user with a question and returns their response.
     *
     * @param prompt Prompt message.
     * @return User's response.
     */
    public String ask(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    /**
     * Displays the list of tasks that match the search keyword.
     *
     * @param foundTasks List of matching tasks.
     */
    public void showFoundTasks(java.util.List<FullMarksBot.Task> foundTasks) {
        System.out.println("Here are the related tasks:");
        for (int i = 0; i < foundTasks.size(); i++) {
            FullMarksBot.Task t = foundTasks.get(i);
            System.out.println("     " + (i + 1) + "." + t.getStatusIcon() + t.getDescription());
        }
    }

    // Helper for GUI: get found tasks string
    public String getFoundTasksString(java.util.List<FullMarksBot.Task> foundTasks) {
        if (foundTasks.isEmpty()) return "No matching tasks found.";
        StringBuilder sb = new StringBuilder("Here are the related tasks:\n");
        for (int i = 0; i < foundTasks.size(); i++) {
            FullMarksBot.Task t = foundTasks.get(i);
            sb.append("     ").append(i + 1).append(".").append(t.getStatusIcon()).append(t.getDescription()).append("\n");
        }
        return sb.toString().trim();
    }
}