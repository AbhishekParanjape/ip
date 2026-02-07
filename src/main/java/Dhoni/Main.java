package Dhoni;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The main entry point for the Dhoni application.
 * This class extends JavaFX Application and sets up the GUI interface.
 */
public class Main extends Application {
    private static final String DEFAULT_FILE_PATH = "Dhoni";
    private Dhoni dhoni;

    /**
     * Constructs a Main object with the default file path.
     */
    public Main() {
        this(DEFAULT_FILE_PATH);
    }

    /**
     * Constructs a Main object with the specified file path.
     * 
     * @param filePath the path to the file where tasks will be stored
     */
    public Main(String filePath) {
        this.dhoni = new Dhoni();
    }

    /**
     * Starts the JavaFX application.
     * Sets up the main window and initializes the application.
     * 
     * @param stage the primary stage for the application
     */
    @Override
    public void start(Stage stage) {
        try {
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setDhoni(dhoni);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
