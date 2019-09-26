package GUI;

import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.fxml.FXMLLoader;

public class TitleScreen extends Application {
  
  private Scene sceneTitle ; // Stores the titleScreen created by this class.
  
  public TitleScreen() {
    sceneTitle = createScene();
  }
  
  public Scene createScene() {
    Parent root = FXMLLoader.load(getClass().getResource("/GUI/sample.fxml")); // Loads the FXML
    // TitleScene Creation
    Scene s = new Scene(root,400,640); // Creates the Scene, "the window" of our program
    
    return s;
  }
  
}