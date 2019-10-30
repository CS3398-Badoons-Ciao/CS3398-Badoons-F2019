package GUI;

import Model.Model;
import Interfaces.CategoryCalculatorInterface;
import Model.Course;
import javafx.stage.*;
import javafx.scene.*;

import java.io.IOException;

/**
 * MainGUI holds references to composed Scene GUI's
 */

public class Scenes {
    private Stage primaryStage;
    private TitleScreen title;
    private CourseOverview courseOverview;
    private TitleAccountCreation accountcreation;
    private CourseScene courseDisplay;

    /**
     * Constructs the main by calling the setGUI method.
     */
    public Scenes(Stage primaryStage) {
        this.primaryStage = primaryStage;

        try {
            this.buildGUIS(); // composes GUI's
        } catch (IOException e) {
            System.out.println("Error loading FXML files." + e);
        }
    }

    private void buildGUIS() throws IOException{
        Model model = new Model();
        title = new TitleScreen(model, this);
        courseOverview = new CourseOverview(model, this);
        accountcreation = new TitleAccountCreation(model, this);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /** TODO IS THIS NEEDED
    public void setCourse(Course c, CategoryCalculatorInterface ccI) {
        courseDisplay = new CourseScene(c, ccI);
    }

    public Scene getCourseScene() {
        return courseDisplay.getScene();
    }
     */

    /**
     * getTitleScene returns the scene of the TitleScreen class.
     * @return Scene of the Title GUI.
     */
    public Scene getTitleScene  () {
        try {
            return title.getScene();
        } catch (IOException e) {
            System.out.println("Error loading TitleScreen fxml" + e);
        }
        return null;
    }

    public Scene getCourseOverviewScene() {

        try {
            return courseOverview.getScene();
        } catch (IOException e) {
            System.out.println("Error loading catalogue fxml" + e);
        }

        return null;
    }

    public Scene getAccountCreationScene() {
        try {
            return accountcreation.getScene();
        } catch (IOException e) {
            System.out.println("Error loading accountcreation.fxml");
        }
        return null;
    }

}
