import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.fxml.FXMLLoader;

class Main extends Application {
  
  
  private Scene titleScreen; // TitleScreen's GUI.
  
  // Main method for running this program.
  public static void main(String[] args) {
    launch(args); // Calls the launcher for the program.
  }
  
  @Override
  public void start(Stage main) throws IOException {    
    
    main.setTitle("Grade Manager"); // Sets the top bar to "Grade Manager"
    main.setScene(titleScene); // Sets the window the title window, "titleScene"
    main.show(); // Displays the current window.
     
  }
  
  public void init() {
    titleScene = titleScene(); // initialize the title screen.
  }

}



