import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class FullMarksBot {
    public static String NAME = "FullMarksBot";

    public static void main(String[] args) {
        System.out.printf("Hello, I'm %s," +
                "the bot that gives you full marks, " +
                "please write down what you want me to store!", NAME);

        Scanner scanner = new Scanner(System.in);
        String input;
        ArrayList<String> tasks = new ArrayList<>();
        while (true) {
            input = scanner.nextLine();

            if (input.equals("list")) {
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i+1) + ": " + tasks.get(i));
                }
                continue;
            }

            if (input.equals("bye")) {
                System.out.println("bye bye for now!");
                break;
            }

            tasks.add(input);
            System.out.println("New Task: " + input);

        }
    }
}
