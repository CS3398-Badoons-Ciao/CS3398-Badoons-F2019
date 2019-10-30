package GUI;

import Model.Model;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class CourseOverview {
    private Scene sceneOverview; // Stores the courseScreen created by this class.

    CourseOverview(Model model, Scenes mainGUI) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("courseoverview.fxml")); // Loads the FXML
        Parent root = loader.load();

        CourseOverviewController controller = loader.getController();
        controller.setModel(model);
        controller.setMainGUI(mainGUI);

        // TitleScene Creation
        sceneOverview = new Scene(root,900,640); // Creates the Scene, "the window" of our program

    }

    public Scene getScene() throws IOException {
        return sceneOverview;
    }


}
