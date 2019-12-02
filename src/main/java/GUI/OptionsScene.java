package GUI;

import Model.Model;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class OptionsScene {
    private Scene optionsScene;
    private OptionsSceneController controller;

    OptionsScene(Model model, Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("OptionsScene.fxml"));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
        controller.setModel(model);

        optionsScene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
    }

    public Scene getScene() {
        return optionsScene;
    }

    public OptionsSceneController getController() {
        return controller;
    }
}



