package GUI;

import Model.Model;
import javafx.scene.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;


public class LogIn {
  private Scene logInScene; // Stores the titleScreen created by this class.

  public LogIn(Model model, Stage primaryStage) {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));

    Parent root = null;
    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    root.setId("AnchorPane"); // Sets the root of the CSS file. Used for the background image.

    LogInController controller = loader.getController();
    controller.setModel(model);
    controller.setPrimaryStage(primaryStage);


    // TitleScene Creation
    logInScene = new Scene(root); // Creates the Scene, "the window" of our program
    logInScene.getStylesheets().addAll(getClass().getResource("resources/titlescreen.css").toExternalForm()); // Loads the CSS file.
  }

  public Scene getScene() throws IOException {
    return logInScene;
  }
  
}