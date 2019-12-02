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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import org.apache.poi.ss.formula.functions.T;

import javax.tools.Tool;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * CourseOverviewController is the FXML controller of courseOverview
 * fxml controller instantiation does not allow constructor parameters.
 * Invariants: setModel(Model)
 *             setMainGUI(MainGUI)
 */
public class CourseOverviewController extends SceneController implements Initializable {
    @FXML
    private VBox root;
    @FXML
    private VBox titleBox;
    @FXML
    private MenuItem Close;
    @FXML
    private MenuItem Help;
    @FXML
    private TextField addCourseTextField;
    @FXML
    private TextField courseSelectionTextField;
    @FXML
    private Text gpaLabel;
    @FXML
    private TableView courseTable;
    @FXML
    private Button addCourseButton;
    @FXML
    private Button changeCourseBtn;
    @FXML
    private Button deleteCourseBtn;
    @FXML
    private Button exportBtn;

    private TableColumn<Course, String>  courseNameColumn;

    /** ObservableList representation of the Category list of Assignment */
    private ObservableList<Course> observableCourses = FXCollections.observableArrayList();

    /**
     * adds Courses for Demo
     */
    @FXML
    private void addDemoCourse() {
        try {
            model.user.addPresentCourse(TestCourseFactory.buildCourse());
            model.user.addPresentCourse(TestCourseFactory.buildCourse2());
            model.user.addPresentCourse(TestCourseFactory.buildCourse3());
            model.user.addPresentCourse(TestCourseFactory.buildCourse4());
            model.user.addPresentCourse(TestCourseFactory.buildCourse5());
        }
        catch (DuplicateNameException d) {
            AlertPopUp.alert(d.title, d.header, d.toString());
        }

        observableCourses.setAll(model.user.getPresentCourses());
        courseTable.setItems(observableCourses);
        loadGPA();
    }

    /**
     * this function handles the File, Help button to open the help menu
     */
    @FXML
    private void help() {
        Popup popup = new Popup();

        final String helpText = "Welcome to the course overview scene. Here you can add new courses,\n" +
                "delete courses, export your courses to excel, or navigate to the \n" +
                "individual course scenes via the view course button.";

        Label label = new Label(helpText);

        Button exitButton = new Button("Close Popup");
        exitButton.setOnAction(addEvent -> popup.hide());

        BoxSplitLayout popupLayout = new BoxSplitLayout();

        //centers popup
        popup.centerOnScreen();

        //define the popups background color
        popupLayout.setBackground(SceneStyle.getSecondaryBackground());

        popupLayout.bodyLayout.getChildren().addAll(label, exitButton);

        popup.getContent().add(popupLayout);
        popup.show(primaryStage);
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

    @FXML
    private void Add(ActionEvent actionEvent) {
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
        observableCourses.setAll(model.user.getPresentCourses());
        courseTable.setItems(observableCourses);
        courseTable.getColumns().add(courseNameColumn);
        loadGPA();
    }

    public void loadGPA() {
        model.cal(); // Calculate GPA
        gpaLabel.setText("GPA : " + model.user.getGPA()); // Display GPA
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        root.setBackground(SceneStyle.getBackground());
        titleBox.setBackground(SceneStyle.getSecondaryBackground());

        courseTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        courseNameColumn = new TableColumn<>("Course Name");
        courseNameColumn.setMinWidth(100);
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("name")); // property must match object
        courseNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        final Tooltip addCourseButtonTooltip = new Tooltip("This button creates a new course");
        final Tooltip viewCourseButtonTooltip = new Tooltip("This button takes you to the Course scene for the selected scene");
        final Tooltip deleteCourseButtonTooltip = new Tooltip("This button deletes the selected course from the system");
        final Tooltip exportButtonTooltip = new Tooltip("This button exports your grade data to an Excel spreadsheet");

        Tooltip.install(addCourseButton, addCourseButtonTooltip);
        Tooltip.install(changeCourseBtn, viewCourseButtonTooltip);
        Tooltip.install(deleteCourseBtn, deleteCourseButtonTooltip);
        Tooltip.install(exportBtn ,exportButtonTooltip);
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
        loadGPA();
    }

    public void handleExportBtn(ActionEvent actionEvent) {
        ExcelFileExporter excelFileExporter = new ExcelFileExporter("src\\main\\java\\GUI\\resources\\ExportedGradeData.xlsx", model.user);
        try {
            excelFileExporter.exportFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
