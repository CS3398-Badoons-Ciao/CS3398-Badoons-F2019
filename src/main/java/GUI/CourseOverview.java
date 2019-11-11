package GUI;

import Model.Model;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CourseOverview {
    private Scene sceneOverview;
    private CourseOverviewController controller;

    CourseOverview(Model model, Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("courseoverview.fxml")); // Loads the FXML

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
        controller.setModel(model);
        controller.load();

        sceneOverview = new Scene(root);
    }

    public Scene getScene() {
        return sceneOverview;
    }

    public CourseOverviewController getController() {
        return controller;
    }
}
