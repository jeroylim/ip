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


    /**
     * Initializes the main window and displays the greeting.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        Ui ui = new Ui();
        fullMarksBot = new FullMarksBot();
        String greeting = ui.showWelcomeString(fullMarksBot.getName());

        dialogContainer.getChildren().add(DialogBox.getDukeDialog(greeting, dukeImage));
    }

    /**
     * Injects the FullMarksBot instance for use in the GUI.
     *
     * @param d FullMarksBot instance.
     */
    public void setFullMarksBot(FullMarksBot d) {
        fullMarksBot = d;
    }

    /**
     * Handles user input from the text field and updates the dialog container.
     */
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

        } catch (FullMarksException e) {
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getDukeDialog(" Error: " + e.getMessage(), dukeImage)
            );
        } catch (Exception e) {
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getDukeDialog(" Oh no, " + e.getMessage(), dukeImage)
            );
        } finally {
            userInput.clear();
        }
    }

}
