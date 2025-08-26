package fullMarksBot;

import java.util.Scanner;

public class Ui {
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome(String NAME) {
        System.out.printf("Hello, I'm %s, the bot that gives you full marks, please write down what you want me to store!%n", NAME);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showTaskList(TaskList taskList) {
        for (int i = 0; i < taskList.size(); i++) {
            FullMarksBot.Task t = taskList.getTask(i);
            System.out.println((i + 1) + ": " + t.getStatusIcon() + t.getDescription());
        }
    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }

    public String ask(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }
}