package main;

import GUI.*;

import java.io.IOException;
import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.fxml.FXMLLoader;

public class Main extends Application {

  @Override
  public void start(Stage main) throws IOException {

    TitleScreen title = new TitleScreen(); // TitleScreen's GUI.

    main.setTitle("Grade Manager"); // Sets the top bar to "Grade Manager"
    main.setScene(title.getScene()); // Sets the window the title window, "titleScene"
    main.show(); // Displays the current window.
     
  }

  // Main method for running this program.
  public static void main(String[] args) {
    launch(args); // Calls the launcher for the program.
  }

}



