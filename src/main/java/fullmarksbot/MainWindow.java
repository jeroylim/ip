package fullmarksbot;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private FullMarksBot fullMarksBot;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/download.jpeg"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/100-emoji.png"));

    // Conversation state
    private enum State {
        NORMAL,          // Just normal command input
        WAITING_TASKTYPE,// User must specify todo/deadline/event
        WAITING_DEADLINE,// User must enter deadline date
        WAITING_EVENT_START,// User must enter event start
        WAITING_EVENT_END   // User must enter event end
    }

    private State state = State.NORMAL;
    private String pendingDescription;  // hold description across states
    private String pendingStartDate;    // hold start date for event

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        Ui ui = new Ui();
        String greeting = String.format("Hello, I'm %s, the bot that gives you full marks, "
                + "please write down what you want me to store!", "FullMarksBot");

        dialogContainer.getChildren().add(DialogBox.getDukeDialog(greeting, dukeImage));
    }

    /** Injects the FullMarksBot instance */
    public void setFullMarksBot(FullMarksBot d) {
        fullMarksBot = d;
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        try {
            String response = fullMarksBot.getResponse(input);

            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getDukeDialog(response, dukeImage)
            );

            if (response.equals("bye bye for now!")) {
                javafx.application.Platform.exit();   // closes JavaFX
            }

        } catch (FullMarksBot.FullMarksException e) {
            // Show error message from your custom exception in the dialog
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getDukeDialog(" Error: " + e.getMessage(), dukeImage)
            );
        } catch (Exception e) {
            // Generic fallback (in case something unexpected happens)
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getDukeDialog(" Oh no, " + e.getMessage(), dukeImage)
            );
        } finally {
            userInput.clear();
        }
    }

}


