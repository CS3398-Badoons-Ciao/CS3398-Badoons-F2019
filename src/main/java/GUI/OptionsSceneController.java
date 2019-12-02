package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OptionsSceneController extends SceneController implements Initializable{
    @FXML
    private VBox root;
    @FXML
    private MenuItem Back;
    @FXML
    private MenuItem Save;
    @FXML
    private MenuItem Close;
    @FXML
    private VBox titleBox;
    @FXML
    private VBox main;
    @FXML
    private HBox primaryPickerBox;
    @FXML
    ColorPicker primaryBackgroundColorPicker = new ColorPicker(SceneStyle.getPrimaryBackgroundColor());
    @FXML
    ColorPicker secondaryBackgroundColorPicker = new ColorPicker(SceneStyle.getSecondaryBackgroundColor());
    @FXML
    private CheckBox fullScreenCheckBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBackgroundColor();
        primaryBackgroundColorPicker.setValue(SceneStyle.getPrimaryBackgroundColor());
        secondaryBackgroundColorPicker.setValue(SceneStyle.getPrimaryBackgroundColor());
    }

    /**
     * saves user data
     */
    @FXML
    private void save() {
        model.saveUser();
    }

    /**
     * changes scene to Menu
     */
    @FXML
    private void back(ActionEvent event) {
        primaryStage.setScene((new Menu(primaryStage, model)).getScene());
    }

    /**
     * close() handles the File, Close button to just go back to the title Screen.
     * This function should ask the user to save their data before closing.
     */
    @FXML
    private void close() throws IOException {
        primaryStage.setScene( (new LogIn(model, primaryStage)).getScene());
    }

    private void setBackgroundColor() {
        root.setBackground(SceneStyle.getPrimaryBackground());
        titleBox.setBackground(SceneStyle.getSecondaryBackground());
    }

    public void changePrimeBackgroundColor(ActionEvent actionEvent) {
        SceneStyle.setPrimaryBackgroundColor(primaryBackgroundColorPicker.getValue());
        setBackgroundColor();
    }

    public void changeSecondaryBackgroundColor(ActionEvent actionEvent) {
        SceneStyle.setSecondaryBackgroundColor(secondaryBackgroundColorPicker.getValue());
        setBackgroundColor();
    }

    public void changeToDefaultColor(ActionEvent actionEvent) {
        SceneStyle.setDefaultColor();
        setBackgroundColor();
    }

    public void changeFullScreen(ActionEvent actionEvent) {
        if (fullScreenCheckBox.isSelected()) {
            primaryStage.setMaximized(true);
        }
        if (!fullScreenCheckBox.isSelected()) {
            primaryStage.setMaximized(false);
        }
    }

}
