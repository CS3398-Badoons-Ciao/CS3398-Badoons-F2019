package GUI;

import Model.Model;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * TitleScreenController is the FXML controller of the TitleScreen
 * fxml controller instantiation does not allow constructor parameters.
 * Invariants (must call):  setModel(Model)
 *                          setMainGUI(MainGUI)
 */
public class LogInController extends SceneController implements Initializable {
    @FXML
    private GridPane grid;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button go;
    @FXML
    private Text title;
    @FXML
    private Button create;
    @FXML
    private Text need;

    /**
     * Go tries to login given a username and password.
     *
     * Go will check the loginField, and passwordField and compare it to a list of names and passwords
     * to see if they match. If it does match, it loads their user data. If it does not, it prompts the user
     * for a retry. A default login of username "test" and password "test" is providd.
     * @param e e
     */
    public void Go(ActionEvent e) {
        String login = loginField.getText();
        String password = passwordField.getText();

        model.login(login,password);

        if (model.user != null) {
            //primaryStage.setScene((new CourseOverview(model, primaryStage)).getScene());
            primaryStage.setScene((new Menu(primaryStage, model)).getScene());
        } else {
            Alert loginError = new Alert(Alert.AlertType.INFORMATION);
            loginError.setTitle("Login Error");
            loginError.setHeaderText("Username or password did not match.");
            loginError.showAndWait();
        }

    }

    public void createAccount(ActionEvent e) throws IOException {
        primaryStage.setScene((new AccountCreation(model, primaryStage)).getScene());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        go.setPrefSize(150,20);
        create.setPrefSize(150,20);
    }
}