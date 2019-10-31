package GUI;

import Factory.TestCourseFactory;
import Model.Model;
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
    private TitleAccountCreation accountCreation;

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
        accountCreation = new TitleAccountCreation(model, this);
    }

    /**
     * loads User course data for CourseOverviewScene
     */
    public void loadCourseOverview() {
        courseOverview.getController().load();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

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
            return accountCreation.getScene();
        } catch (IOException e) {
            System.out.println("Error loading accountcreation.fxml");
        }
        return null;
    }

}
