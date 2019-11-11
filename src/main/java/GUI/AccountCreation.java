package GUI;

import Model.Model;
import javafx.scene.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

public class AccountCreation {
    private Scene AccountCreationScene;

    AccountCreation(Model model, Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("accountcreation.fxml")); // Loads the FXML
        Parent root = loader.load();
        root.setId("AnchorPane"); // Sets the root of the CSS file. Used for the background image.

        AccountCreationController controller = loader.getController();
        controller.setModel(model);
        controller.setPrimaryStage(primaryStage);

        // TitleScene Creation
        AccountCreationScene = new Scene(root,900,640); // Creates the Scene, "the window" of our program
    }

    public Scene getScene() throws IOException {
        return AccountCreationScene;
    }
}
