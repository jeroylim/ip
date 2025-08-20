import java.util.Scanner;

public class FullMarksBot {
    public static String NAME = "FullMarksBot";

    public static void main(String[] args) {
        System.out.printf("Hello, I'm %s," +
                "the bot that gives you full marks", NAME);

        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.println("bye bye for now!");
                break;
            }
            System.out.println("You just said: " + input);
        }
    }
}
