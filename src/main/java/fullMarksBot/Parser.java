package fullMarksBot;

/**
 * Parses user input into commands and arguments for FullMarksBot.
 */
public class Parser {
    /**
     * Returns the command word from the user's input.
     *
     * @param input User input string.
     * @return Command word in lowercase.
     */
    public static String getCommandWord(String input) {
        String[] parts = input.trim().split(" ");
        return parts[0].toLowerCase();
    }

    /**
     * Returns the zero-based task number from the user's input.
     *
     * @param input User input string containing the task number.
     * @return Zero-based index of the task.
     * @throws FullMarksBot.FullMarksException If the task number is missing or invalid.
     */
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