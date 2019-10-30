package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * TitleAccountController is the FXML controller of accountCreation
 * fxml controller instantiation does not allow constructor parameters.
 * Invariants: setModel(Model)
 *             setMainGUI(MainGUI)
 */
public class TitleAccountController extends SceneController {
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


        // Check if there already exists a username.
        // Might need to discuss with model how to get the DB.
//        if (Main.model.dbManager.checkUser(user) != -1) {
//            Alert nameError = new Alert(Alert.AlertType.INFORMATION);
//            nameError.setTitle("Duplicate Username");
//            nameError.setHeaderText("Your username already exists.");
//            nameError.showAndWait();
//        } else if (!pass.equals(confirmPass)) { // Check if the passwords don't match, and then prompt the user.
//            Alert nameError = new Alert(Alert.AlertType.INFORMATION);
//            nameError.setTitle("Password Error");
//            nameError.setHeaderText("Your confirmed password does not match the password you entered.");
//            nameError.showAndWait();
//        } else { // Create the account.
//            Main.model.createNewUser(user, id, pass); // Create a user with the inputs.
//            Main.model.login(id, pass);
//            Main.mainStage.setScene(Main.gui.getCourseOverviewScene());
//        }

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
            mainGUI.getPrimaryStage().setScene(mainGUI.getCourseOverviewScene());
        }
    }

    public void back(ActionEvent e) {
        mainGUI.getPrimaryStage().setScene(mainGUI.getTitleScene()); // Sets the scene back to the title screen.
    }
}
