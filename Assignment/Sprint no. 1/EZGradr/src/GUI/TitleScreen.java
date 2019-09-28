package GUI;

import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.fxml.FXMLLoader;
import java.io.IOException;


public class TitleScreen {
  
  private Scene sceneTitle ; // Stores the titleScreen created by this class.
  
  public TitleScreen() throws IOException {
    Parent root = FXMLLoader.load(this.getClass().getClassLoader().getResource("resources/sample.fxml")); // Loads the FXML
    // TitleScene Creation
    sceneTitle = new Scene(root,400,640); // Creates the Scene, "the window" of our program
  }
  
  public Scene getScene() throws IOException {
    return sceneTitle;
  }
  
}