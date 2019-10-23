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

public class TitleAccountController {
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

        if (Main.model.checkUser(id) != -1) {
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
            Main.model.createNewUser(user, id, pass); // Create a user with the inputs.
            Main.model.login(id, pass);
            Main.mainStage.setScene(Main.gui.getCourseOverviewScene());
        }
    }

    public void back(ActionEvent e) {
        Main.mainStage.setScene(Main.gui.getTitleScene()); // Sets the scene back to the title screen.
    }
}
