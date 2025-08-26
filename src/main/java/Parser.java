public class Parser {
    public static String getCommandWord(String input) {
        String[] parts = input.trim().split(" ");
        return parts[0].toLowerCase();
    }

    public static int getTaskNumber(String input) throws FullMarksBot.FullMarksException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new FullMarksBot.FullMarksException("Please specify a task number.");
        }
        try {
            return Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new FullMarksBot.FullMarksException("Invalid task number. Please enter a number.");
        }
    }
}