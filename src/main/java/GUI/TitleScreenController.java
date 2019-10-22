package GUI;

import main.*;

import javafx.scene.text.Text;
import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.fxml.*;
import javafx.scene.control.*;

/**
 * TitleScreenController is the FXML controller of of the TitleScreen.
 */
public class TitleScreenController {
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;

    /**
     * Go tries to login given a username and password.
     *
     * Go will check the loginField, and passwordField and compare it to a list of names and passwords
     * to see if they match. If it does match, it loads their user data. If it does not, it prompts the user
     * for a retry. A default login of username "test" and password "test" is providd.
     * @param e e
     */
    public void Go(ActionEvent e) {
        // Default test login.
        String login = loginField.getText();
        String password = passwordField.getText();

        Main.model.login(login,password);

        if (Main.model.user != null) {
            Main.mainStage.setScene(Main.gui.getCourseOverviewScene());
            passwordField.setText(""); // Resets the text.
        } else {
            Alert loginError = new Alert(Alert.AlertType.INFORMATION);
            loginError.setTitle("Login Error");
            loginError.setHeaderText("Username or password did not match.");
            loginError.showAndWait();
        }

    }

    public void createAccount(ActionEvent e) {
        Main.mainStage.setScene(Main.gui.getAccountCreationScene()); // Set the Scene to Account Creation.
    }
}