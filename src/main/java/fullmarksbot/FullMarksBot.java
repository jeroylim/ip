package fullmarksbot;

import java.time.LocalDateTime;
import java.util.regex.Pattern;
import java.time.format.DateTimeFormatter;

/**
 * Main class for FullMarksBot, a bot for managing tasks.
 */
public class FullMarksBot {
    private static final String FILE_PATH = "./data/tasks.txt";





    /**
     * Runs the main loop of FullMarksBot.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
    }

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui = new Ui();
    private final Parser parser = new Parser();

    /**
     * Constructs a FullMarksBot instance, loading tasks from storage.
     */
    public FullMarksBot() {
        this.storage = new Storage(FILE_PATH);
        this.tasks = storage.loadTasks();
    }

    private enum State {
        NORMAL,
        WAITING_TASKTYPE,
        WAITING_DEADLINE,
        WAITING_EVENT_START,
        WAITING_EVENT_END
    }

    private State state = State.NORMAL;
    private String pendingDescription;
    private String pendingStartDate;

    /**
     * Unified entry point for GUI. Processes user input and returns bot response.
     *
     * @param input User input string.
     * @return Bot response string.
     * @throws FullMarksException If an error occurs during processing.
     */
    public String getResponse(String input) throws FullMarksException {
        switch (state) {
        case NORMAL:
            return handleNormal(input);

        case WAITING_TASKTYPE:
            return handleTaskType(input);

        case WAITING_DEADLINE:
            return handleDeadline(input);

        case WAITING_EVENT_START:
            return handleEventStart(input);

        case WAITING_EVENT_END:
            return handleEventEnd(input);

        default:
            throw new FullMarksException("What?... Please try again.");
            }
    }

    private String handleNormal(String input) throws FullMarksException {
        String command = Parser.getCommandWord(input);
        switch (command) {
        case "list":
            return ui.getTaskListString(tasks);

        case "mark":
            int markIdx = Parser.getTaskNumber(input);
            tasks.markTask(markIdx);
            storage.saveTasks(tasks);
            return "Congrats! You completed this task!";

        case "unmark":
            int unmarkIdx = Parser.getTaskNumber(input);
            tasks.unmarkTask(unmarkIdx);
            storage.saveTasks(tasks);
            return "Oh no! Let me unmark this...";

        case "delete":
            int delIdx = Parser.getTaskNumber(input);
            tasks.deleteTask(delIdx);
            storage.saveTasks(tasks);
            return "Let's get this task out of here.";

        case "find":
            String keyword = Parser.getFindKeyword(input);
            return ui.getFoundTasksString(tasks.findTasks(keyword));

        case "bye":
            return "bye bye for now!";

        default:
            pendingDescription = input.trim();
            state = State.WAITING_TASKTYPE;
            return "Is this a Todo, Deadline or Event task?";
        }
    }

    private String handleTaskType(String type) throws FullMarksException {
        if (parser.containsExactWord(type, "todo")) {
            tasks.addTask(new Todo(pendingDescription));
            storage.saveTasks(tasks);
            String toReturn = "New Todo: " + pendingDescription;
            resetState();
            return toReturn;

        } else if (parser.containsExactWord(type, "deadline")) {
            state = State.WAITING_DEADLINE;
            return "When should it be done by? (yyyy-MM-ddTHH:mm)";

        } else if (parser.containsExactWord(type, "event")) {
            state = State.WAITING_EVENT_START;
            return "When does this event start? (yyyy-MM-ddTHH:mm)";
        } else {
            resetState();
            throw new FullMarksException("Invalid type. Please type 'todo', 'deadline' or 'event'.");
        }
    }

    private String handleDeadline(String endDate) throws FullMarksException {
        if (endDate.trim().isEmpty()) {
            throw new FullMarksException("Deadline date cannot be empty.");
        }
        tasks.addTask(new Deadline(pendingDescription, endDate));
        storage.saveTasks(tasks);
        String toReturn = "New Deadline: " + pendingDescription;
        resetState();
        return toReturn;
    }

    private String handleEventStart(String startDate) throws FullMarksException {
        if (startDate.trim().isEmpty()) {
            throw new FullMarksException("Start date cannot be empty.");
        }
        pendingStartDate = startDate;
        state = State.WAITING_EVENT_END;
        return "Okay, now give me the end date (yyyy-MM-ddTHH:mm)";
    }

    private String handleEventEnd(String endDate) throws FullMarksException {
        if (endDate.trim().isEmpty()) {
            throw new FullMarksException("End date cannot be empty.");
        }
        tasks.addTask(new Event(pendingDescription, pendingStartDate, endDate));
        storage.saveTasks(tasks);
        String toReturn = "New Event: " + pendingDescription;
        resetState();
        return toReturn;
    }

    private void resetState() {
        state = State.NORMAL;
        pendingDescription = null;
        pendingStartDate = null;
    }

}
