package GUI;

import Model.Model;

/**
 * injects FXML controllers with model and mainGUI references as FXML Controllers
 * do not accept constructor parameters when attached to fxml files
 */
public abstract class SceneController {
    Model model;
    Scenes mainGUI;

    public void setModel(Model model) {
        this.model = model;
    }

    void setMainGUI(Scenes mainGUI) {
        this.mainGUI = mainGUI;
    }
}
