package GUI;

import main.*;

import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class CourseOverviewController {
    @FXML
    private MenuItem Close;

    /**
     * close() handles the File, Close button to just go back to the title Screen.
     * This function should ask the user to save their data before closing.
     */
    public void close() {
        Main.displayTitle();
    }
}
