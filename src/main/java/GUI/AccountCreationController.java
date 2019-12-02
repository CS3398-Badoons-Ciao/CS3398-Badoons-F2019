package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * TitleAccountController is the FXML controller of accountCreation
 * fxml controller instantiation does not allow constructor parameters.
 * Invariants: setModel(Model)
 *             setMainGUI(MainGUI)
 */
public class AccountCreationController extends SceneController implements Initializable {
    @FXML
    private VBox root;
    @FXML
    private TextField userID;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField confirmPassword;


    public void Go(ActionEvent e) {
        String id = userID.getText();
        String user = username.getText();
        String pass = password.getText();
        String confirmPass = confirmPassword.getText();

        if (model.checkUser(id) != -1) {
            Alert nameError = new Alert(Alert.AlertType.INFORMATION);
            nameError.setTitle("Duplicate Username");
            nameError.setHeaderText("Your username already exists.");
            nameError.showAndWait();
        } else if (!pass.equals(confirmPass)) { // Check if the passwords don't match, and then prompt the user.
            Alert nameError = new Alert(Alert.AlertType.INFORMATION);
            nameError.setTitle("Password Error");
            nameError.setHeaderText("Your confirmed password does not match the password you entered.");
            nameError.showAndWait();
        } else { // Create the account.
            model.createNewUser(user, id, pass); // Create a user with the inputs.
            model.login(id, pass);
            primaryStage.setScene((new CourseOverview(model, primaryStage)).getScene());
        }
    }

    public void back(ActionEvent e) throws IOException {
        primaryStage.setScene((new LogIn(model, primaryStage)).getScene());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        root.setBackground(SceneStyle.getBackground());
    }
}
