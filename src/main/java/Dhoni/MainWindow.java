package Dhoni;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI window.
 * This class handles the interaction between the user interface and the Dhoni application logic.
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

    private Dhoni dhoni;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dhoniImage = new Image(this.getClass().getResourceAsStream("/images/DaDhoni.png"));

    /**
     * Initializes the main window.
     * Binds the scroll pane's vertical value to the dialog container's height property.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Dhoni instance into this controller.
     * 
     * @param d the Dhoni application instance
     */
    public void setDhoni(Dhoni d) {
        dhoni = d;
    }

    /**
     * Handles user input from the text field.
     * Creates dialog boxes for both user input and Dhoni's response, then clears the input field.
     * 
     * @throws Exception if there's an error processing the user input
     */
    @FXML
    private void handleUserInput() throws Exception {
        String input = userInput.getText();
        String response = dhoni.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDhoniDialog(response, dhoniImage)
        );
        userInput.clear();
    }
}
