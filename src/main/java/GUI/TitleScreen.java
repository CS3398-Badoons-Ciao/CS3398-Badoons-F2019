package GUI;

import Model.Model;
import javafx.scene.*;
import javafx.fxml.FXMLLoader;
import java.io.IOException;


public class TitleScreen {
  private Scene sceneTitle; // Stores the titleScreen created by this class.

  TitleScreen(Model model, Scenes mainGUI) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("titlescreen.fxml"));
    Parent root = loader.load();
    root.setId("AnchorPane"); // Sets the root of the CSS file. Used for the background image.

    TitleScreenController controller = loader.getController();
    controller.setModel(model);
    controller.setMainGUI(mainGUI);


    // TitleScene Creation
    sceneTitle = new Scene(root,900,640); // Creates the Scene, "the window" of our program
    sceneTitle.getStylesheets().addAll(getClass().getResource("resources/titlescreen.css").toExternalForm()); // Loads the CSS file.
  }

  public Scene getScene() throws IOException {
    return sceneTitle;
  }
  
}