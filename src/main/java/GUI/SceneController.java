package GUI;

import Model.Model;
import javafx.stage.Stage;

/**
 * injects FXML controllers with model and mainGUI references as FXML Controllers
 * do not accept constructor parameters when attached to fxml files
 */
public abstract class SceneController {
    Model model;
    Stage primaryStage;

    public void setModel(Model model) {
        this.model = model;
    }
    void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
