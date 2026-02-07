package Dhoni;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.Collections;

/**
 * DialogBox represents a chat bubble in the GUI.
 * It contains an ImageView to represent the speaker's face and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Constructs a DialogBox with the given text and image.
     * 
     * @param text the text to display in the dialog
     * @param img the image to display as the speaker's avatar
     */
    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
        
        // Load CSS styles for dialog boxes
        this.getStylesheets().add(getClass().getResource("/css/dialog-box.css").toExternalForm());
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     * This is used for the bot's responses to distinguish them from user messages.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    /**
     * Creates a user dialog box.
     * 
     * @param text the text to display
     * @param img the user's avatar image
     * @return a new DialogBox for user messages
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Creates a Dhoni (bot) dialog box.
     * The dialog is flipped to distinguish it from user messages.
     * 
     * @param text the text to display
     * @param img the bot's avatar image
     * @return a new DialogBox for bot messages
     */
    public static DialogBox getDhoniDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}
