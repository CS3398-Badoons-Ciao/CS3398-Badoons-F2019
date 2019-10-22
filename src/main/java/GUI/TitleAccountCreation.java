package GUI;

import javafx.scene.*;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class TitleAccountCreation {

    private Scene titleAccount;

    public TitleAccountCreation() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("accountcreation.fxml")); // Loads the FXML
        root.setId("AnchorPane"); // Sets the root of the CSS file. Used for the background image.

        // TitleScene Creation
        titleAccount = new Scene(root,900,640); // Creates the Scene, "the window" of our program
    }

    public Scene getScene() throws IOException {
        return titleAccount;
    }
}
