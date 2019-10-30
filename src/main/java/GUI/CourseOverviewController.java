package GUI;

import Factory.TestCourseFactory;
import Model.Calculator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * CourseOverviewController is the FXML controller of courseOverview
 * fxml controller instantiation does not allow constructor parameters.
 * Invariants: setModel(Model)
 *             setMainGUI(MainGUI)
 */
public class CourseOverviewController extends SceneController {
    @FXML
    private MenuItem Close;

    /**
     * close() handles the File, Close button to just go back to the title Screen.
     * This function should ask the user to save their data before closing.
     */
    public void close() {
        mainGUI.getPrimaryStage().setScene(mainGUI.getTitleScene());
    }

    /*
     * Example - Note: can access Course Collection through model base reference
     */
    @FXML
    public void changeCourse(ActionEvent actionEvent) {
        CourseScene courseScene = new CourseScene(mainGUI.getCourseOverviewScene(),
                TestCourseFactory.buildCourse(), new Calculator(), model);
        mainGUI.getPrimaryStage().setScene(courseScene.getScene());
    }


    /*
     * End Example
     */



    public void Add(ActionEvent actionEvent) {

    }
}
