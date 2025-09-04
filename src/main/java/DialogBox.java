import java.io.IOException;
import java.util.Collections;

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

public class DialogBox extends HBox {

    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    public DialogBox(String text, Image img) {
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
    }
    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    public static DialogBox getUserDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.displayPicture.getStyleClass().add("user-picture");
        db.dialog.getStyleClass().add("dialog-label");
        return db;
    }

    public static DialogBox getDuckyDialog(String text, Image img, String cmdType) {
        DialogBox db = new DialogBox(text, img);
        db.displayPicture.getStyleClass().add("ducky-picture");
        db.dialog.getStyleClass().add("reply-label");
        db.flip();

        switch(cmdType) {
        case "SUCCESS":
            db.dialog.getStyleClass().add("success-label");
            break;
        case "LIST", "BYE", "HI":
            db.dialog.getStyleClass().add("normal-label");
            break;
        case "DEL":
            db.dialog.getStyleClass().add("error-label");
            break;
        }
        return db;
    }
}