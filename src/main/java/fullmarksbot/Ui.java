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
}