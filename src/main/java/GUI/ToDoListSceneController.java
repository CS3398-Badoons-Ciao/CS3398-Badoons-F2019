package GUI;

import Factory.TestCourseFactory;
import Interfaces.Listener;
import Interfaces.Publisher;
import Exception.*;
import Model.List;
import Model.ListObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Popup;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ToDoListSceneController extends SceneController implements Initializable, Listener {
    @FXML
    private MenuItem Back;

    @FXML
    private TextField ToDoListText;

    @FXML
    private TableView InProgressList;

    @FXML
    private Button FishishedButton;


    private TableColumn<ListObject, String> AssignmentCol;

    private ObservableList<List> observableList = FXCollections.observableArrayList();
    List listObj = new List(model);

    public void Back() throws IOException {
        primaryStage.setScene( (new LogIn(model, primaryStage)).getScene());
    }

    public void loadInProgress() {
        for (int i = 0; i < listObj.getAssignmentList().size(); i++) {
            //register(listO)
        }
        observableList.setAll(listObj.getAssignmentList());
        InProgressList.setItems(observableList);
        InProgressList.getColumns().add(AssignmentCol);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InProgressList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        AssignmentCol = new TableColumn<>("Assignment");
        AssignmentCol.setMinWidth(100);
        AssignmentCol.setCellValueFactory(new PropertyValueFactory<>("name")); // property must match object
        AssignmentCol.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    @Override
    public void update(Publisher publisher) {
        InProgressList.refresh();
    }

    @Override
    public void register(Publisher publisher) {
        publisher.addListener(this);
    }
}
