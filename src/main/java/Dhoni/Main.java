package Dhoni;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The main entry point for the Dhoni application.
 */
public class Main extends Application {
    private static final String DEFAULT_FILE_PATH = "Dhoni";
    private Dhoni dhoni = new Dhoni();
    // Existing constructor
    public Main(String filePath) {
        // ...
    }

    // Overloaded constructor
    /**
     * Constructor for Main class with default file path.
     */
    public Main() {
        this(DEFAULT_FILE_PATH);
    }

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