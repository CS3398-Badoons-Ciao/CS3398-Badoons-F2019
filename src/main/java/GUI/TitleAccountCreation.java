package GUI;

import Model.Model;
import javafx.scene.*;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class TitleAccountCreation {
    private Scene titleAccount;

    TitleAccountCreation(Model model, Scenes mainGUI) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("accountcreation.fxml")); // Loads the FXML
        Parent root = loader.load();
        root.setId("AnchorPane"); // Sets the root of the CSS file. Used for the background image.

        TitleAccountController controller = loader.getController();
        controller.setModel(model);
        controller.setMainGUI(mainGUI);

        // TitleScene Creation
        titleAccount = new Scene(root,900,640); // Creates the Scene, "the window" of our program
    }

    public Scene getScene() throws IOException {
        return titleAccount;
    }
}
