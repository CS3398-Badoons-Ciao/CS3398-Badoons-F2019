package GUI;

import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.io.IOException;

public class CourseOverview {

    private Scene sceneOverview; // Stores the courseScreen created by this class.

    public CourseOverview() throws IOException {
        // This resolved my problem; check if this version breaks your build.
        // My project now displays GUI correctly.
        // JP
        Parent root = FXMLLoader.load(getClass().getResource("courseoverview.fxml")); // Loads the FXML

        // TitleScene Creation
        sceneOverview = new Scene(root,900,640); // Creates the Scene, "the window" of our program
    }

    public Scene getScene() throws IOException {
        return sceneOverview;
    }

}
