package GUI;

import javafx.scene.control.Alert;

public class AlertPopUp {
    public static void alert(String title, String header, String body) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(body);
        alert.showAndWait();
    }
}
