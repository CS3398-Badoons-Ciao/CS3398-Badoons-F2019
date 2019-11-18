package GUI;

import Factory.TestCourseFactory;
import Interfaces.Listener;
import Interfaces.Publisher;
import Exception.*;
import Model.Calculator;
import Model.Course;
import Model.ExcelFileExporter;
import Model.School;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * CourseOverviewController is the FXML controller of courseOverview
 * fxml controller instantiation does not allow constructor parameters.
 * Invariants: setModel(Model)
 *             setMainGUI(MainGUI)
 */
public class CourseOverviewController extends SceneController implements Initializable, Listener {
    @FXML
    private MenuItem Close;

    @FXML
    private TextField addCourseTextField;

    @FXML
    private TextField courseSelectionTextField;

    @FXML
    private TableView courseTable;

    private TableColumn<Course, String>  courseNameColumn;

    /** ObservableList representation of the Category list of Assignment */
    private ObservableList<Course> observableCourses = FXCollections.observableArrayList();

    /**
     * close() handles the File, Close button to just go back to the title Screen.
     * This function should ask the user to save their data before closing.
     */
    public void close() throws IOException {
        primaryStage.setScene( (new LogIn(model, primaryStage)).getScene());
    }

    public void Add(ActionEvent actionEvent) {
        try {
            String courseName = addCourseTextField.getText();

            // TODO school handle School input
            Course course = new Course(courseName, new School("Texas State University"));

            model.user.addPresentCourse(course);
            observableCourses.add(course);
        }
        catch (DuplicateNameException d) {
            AlertPopUp.alert(d.title, d.header, d.toString());
        }
    }

    public void load() {
        for (Course course : model.user.getPresentCourses()) {
            register(course);
        }

        observableCourses.setAll(model.user.getPresentCourses());
        courseTable.setItems(observableCourses);
        courseTable.getColumns().add(courseNameColumn);
    }

    public void save() {
        model.saveUser();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        courseTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        courseNameColumn = new TableColumn<>("Course Name");
        courseNameColumn.setMinWidth(100);
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("name")); // property must match object
        courseNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    public void handleChangeCourseBtn(ActionEvent actionEvent) {
        Course selectedCourse = (Course) courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            CourseScene courseScene = new CourseScene(primaryStage, selectedCourse, new Calculator(), model);
            primaryStage.setScene(courseScene.getScene());
        }
    }

    public void handleDeleteCourseBtn(ActionEvent actionEvent) {
        Course selectedCourse = (Course) courseTable.getSelectionModel().getSelectedItem();
        model.user.getPresentCourses().remove(selectedCourse);
        observableCourses.remove(selectedCourse);
    }

    public void handleExportBtn(ActionEvent actionEvent) {
        ExcelFileExporter excelFileExporter = new ExcelFileExporter("ExportedGradeData.xlsx", model.user);
        try {
            excelFileExporter.exportFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addDemoCourse() {
        try {
            model.user.addPresentCourse(TestCourseFactory.buildCourse());
            model.user.addPresentCourse(TestCourseFactory.buildCourse2());
        }
        catch (DuplicateNameException d) {
            AlertPopUp.alert(d.title, d.header, d.toString());
        }

        observableCourses.setAll(model.user.getPresentCourses());
        courseTable.setItems(observableCourses);
    }


    @Override
    public void update(Publisher publisher) {
        courseTable.refresh();
    }

    @Override
    public void register(Publisher publisher) {
        publisher.addListener(this);
    }
}
