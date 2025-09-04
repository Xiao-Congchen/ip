import ducky.Ducky;

import ducky.stringprocessing.Parser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller for the main GUI.
 * The design of the GUI is separated out.
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

    private Ducky ducky;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/Pixel_human.png"));
    private Image duckyImage = new Image(this.getClass().getResourceAsStream("/images/Pixel_ducky.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Ducky instance */
    public void setDucky(Ducky d) {
        ducky = d;
    }

    public void startDucky() {
        dialogContainer.getChildren().add(
                DialogBox.getDuckyDialog(ducky.welcome(), duckyImage, "LIST")
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = ducky.simulator(input);
        String lastCmd = Parser.getLastCmd();
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDuckyDialog(response, duckyImage, lastCmd)
        );
        userInput.clear();
        if (lastCmd.equals("BYE")) {
            Stage stage = (Stage) sendButton.getScene().getWindow();
            stage.close(); // Close the program window
            System.exit(0);  // Terminate program
        }
    }
}