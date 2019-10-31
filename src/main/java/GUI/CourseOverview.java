package GUI;

import Model.Model;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class CourseOverview {
    private Scene sceneOverview; // Stores the courseScreen created by this class.
    private CourseOverviewController controller;

    CourseOverview(Model model, Scenes mainGUI) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("courseoverview.fxml")); // Loads the FXML
        Parent root = loader.load();

        controller = loader.getController();
        controller.setModel(model);
        controller.setMainGUI(mainGUI);

        // TitleScene Creation
        sceneOverview = new Scene(root); // Creates the Scene, "the window" of our program

    }

    public Scene getScene() throws IOException {
        return sceneOverview;
    }

    public CourseOverviewController getController() {
        return controller;
    }
}
