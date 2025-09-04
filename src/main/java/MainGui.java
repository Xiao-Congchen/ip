import java.io.IOException;

import ducky.Ducky;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class MainGui extends Application {

    private Ducky ducky = new Ducky();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainGui.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(220);
            stage.setMinWidth(420);
            fxmlLoader.<MainWindow>getController().setDucky(ducky);  // inject the Ducky instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}