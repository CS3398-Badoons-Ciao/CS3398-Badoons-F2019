package GUI;

import com.sun.javafx.iio.ios.IosDescriptor;
import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

/**
 * MainGUI is the overarching GUI class.
 *
 * MainGUI contains all the GUIS for this program, these including...
 * TitleScreen
 * CourseOverview
 */

public class MainGUI {

    private TitleScreen title;
    private CourseOverview catalogue;
    private TitleAccountCreation accountcreation;

    /**
     * Constructs the main by calling the setGUI method.
     */
    public MainGUI() {
        try {
            this.setGUIS(); // Sets the GUIS within the object
        } catch (IOException e) {
            System.out.println("Error loading FXML files." + e);
        }
    }

    private void setGUIS() throws IOException{
        title = new TitleScreen();
        catalogue = new CourseOverview();
        accountcreation = new TitleAccountCreation();
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
            return catalogue.getScene();
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
