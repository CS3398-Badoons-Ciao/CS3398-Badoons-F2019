package GUI;

import javafx.scene.*;
import javafx.fxml.FXMLLoader;
import java.io.IOException;


public class TitleScreen {
  
  private Scene sceneTitle; // Stores the titleScreen created by this class.
  
  public TitleScreen() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("titlescreen.fxml")); // Loads the FXML
    root.setId("AnchorPane"); // Sets the root of the CSS file. Used for the background image.

    // TitleScene Creation
    sceneTitle = new Scene(root,900,640); // Creates the Scene, "the window" of our program
    sceneTitle.getStylesheets().addAll(getClass().getResource("resources/titlescreen.css").toExternalForm()); // Loads the CSS file.
  }
  
  public Scene getScene() throws IOException {
    return sceneTitle;
  }
  
}