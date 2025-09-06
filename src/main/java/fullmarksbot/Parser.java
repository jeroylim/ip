package fullmarksbot;

import java.util.regex.Pattern;

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
        assert input != null : "Input to getCommandWord should not be null";
        String[] parts = input.trim().split(" ");
        assert parts.length > 0 : "Input should contain at least one word";
        return parts[0].toLowerCase();
    }

    /**
     * Returns the zero-based task number from the user's input.
     *
     * @param input User input string containing the task number.
     * @return Zero-based index of the task.
     * @throws FullMarksException If the task number is missing or invalid.
     */
    public static int getTaskNumber(String input) throws FullMarksException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new FullMarksException("Please specify a task number.");
        }
        try {
            int idx = Integer.parseInt(parts[1]) - 1;
            assert idx >= 0 : "Task number should be positive";
            return idx;
        } catch (NumberFormatException e) {
            throw new FullMarksException("Invalid task number. Please enter a number.");
        }
    }

    /**
     * Returns the keyword for the find command from the user's input.
     *
     * @param input User input string containing the find command.
     * @return Keyword to search for.
     * @throws FullMarksException If the keyword is missing.
     */

    public static String getFindKeyword(String input) throws FullMarksException {
    assert input != null : "Input to getFindKeyword should not be null";

        String[] parts = input.trim().split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new FullMarksException("Please specify a keyword to find.");
        }
        return parts[1].trim();
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
}