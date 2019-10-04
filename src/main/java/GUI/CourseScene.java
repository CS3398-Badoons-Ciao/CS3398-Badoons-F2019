package GUI;

import Factory.AssignmentFactory;
import Interfaces.AssignmentInterface;
import Interfaces.CategoryInterface;
import Interfaces.Listener;
import Interfaces.Publisher;
import Model.Category;
import Model.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class CourseScene implements Listener, EventHandler<ActionEvent> {
    // TODO facade to System reference
    private Course course;
    private Scene scene;
    private List<CTableView> categoryTables = new ArrayList<>(); // to allow arrangement
    private ComboBox dropDownList;

    /**
     * sets category and builds scene
      * @param course
     */
    public CourseScene(Course course) {
        this.course = course;
        course.addListener(this);
        buildScene();
    }

    public Scene getScene() {return scene;}

    @Override
    public void update(Publisher model) {
        buildScene();
    }

    @Override
    public void register(Publisher publisher) {
        publisher.addListener(this);
    }

    private void buildScene() {
        // creates scene title
        final Label sceneTitle = new Label(course.getName());
        sceneTitle.setFont(new Font("Arial", 20));

        // creates layout manager
        VBox vBox = new VBox(sceneTitle);
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(10, 10, 10, 10));

        buildCategoryTables();

        // adds tables to layout manager
        for (CTableView cTableView : categoryTables) {
            Label categoryLabel = new Label(cTableView.tableCategory.getName());
            categoryLabel.setFont(new Font("Arial", 20));
            vBox.getChildren().add(categoryLabel);
            vBox.getChildren().add(cTableView);
        }

        // creates 'add assignment'feature
        ObservableList<String> dropDownListCategories = FXCollections.observableArrayList();
        for (CTableView cTableView : categoryTables) {
            dropDownListCategories.add(cTableView.tableCategory.getName());
        }

        dropDownList = new ComboBox(dropDownListCategories);
        dropDownList.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                // TODO highlight table to add course to
            }
        });
        dropDownList.setPromptText("Category");


        final TextField addCourseName = new TextField();
        addCourseName.setPromptText("Course Name");

        final TextField addCurrentGrade = new TextField();
        addCurrentGrade.setPromptText("Current Grade");

        final TextField addPotentialGrade = new TextField();
        addPotentialGrade.setPromptText("Potential Grade");

        final Button addCourseBtn = new Button("+");
        addCourseBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent addEvent) {
                // TODO check data && handle
                for (CTableView c : categoryTables) {
                    if (c.tableCategory.getName() == (String) dropDownList.getValue()) {
                        try{
                            String courseName = addCourseName.getText();
                            double currentGrade = Double.parseDouble(addCurrentGrade.getText());
                            double potentialGrade = Double.parseDouble(addPotentialGrade.getText());
                            c.tableCategory.addAssignment(AssignmentFactory.createAssignment(
                                    courseName, potentialGrade, currentGrade));
                            // TODO create new table row
                        } catch(Exception exception) {
                            System.out.println(exception.getMessage()); // TODO delete
                        } // TODO handle pop-up bad format
                    }
                }

                addCourseName.clear();
                addCurrentGrade.clear();
                addPotentialGrade.clear();
            }
        });

        final HBox addCourseBox = new HBox();
        addCourseBox.getChildren().addAll(
                dropDownList, addCourseName, addCurrentGrade, addPotentialGrade, addCourseBtn);
        addCourseBox.setSpacing(3);


        // creates Save and Exit buttons
        Button saveBtn = new Button("Save");
        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               // TODO call facade method to handle
            }
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //  TODO call facade method to handle
            }
        });

        final HBox menuBox = new HBox();
        menuBox.getChildren().addAll(saveBtn, exitBtn);
        menuBox.setSpacing(3);

        vBox.getChildren().addAll(addCourseBox, menuBox);
        scene = new Scene(vBox);
    }


    private void buildCategoryTables() {
        ArrayList<Category> categories = course.getCategories();

        for (Category category : categories) {
            // creates cTableView
            CTableView cTableView = new CTableView(category);
            //CTableView.
            cTableView.setEditable(true);

            // creates a name column for the cTableView
            TableColumn<AssignmentInterface, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setMinWidth(150);
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name")); // property must match object
            nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            // TODO verify incoming data
            // TODO cell may be updated twice - through both TableColumn && Listener
            nameColumn.setOnEditCommit(
            event -> event.getTableView().getItems().get(
                    event.getTablePosition().getRow()).setName(event.getNewValue())
            );

            // creates a grade column for the cTableView
            TableColumn<AssignmentInterface, Double> gradeColumn = new TableColumn<>("Grade");
            gradeColumn.setMinWidth(150);
            gradeColumn.setCellValueFactory(new PropertyValueFactory<>("currentGrade"));
            // TODO handle for double

            // creates a potential grade column for the cTableView
            TableColumn<AssignmentInterface, String> potentialGradeColumn = new TableColumn<>("Potential Grade");
            potentialGradeColumn.setMinWidth(150);
            potentialGradeColumn.setCellValueFactory(new PropertyValueFactory<>("potentialGrade"));
            // TODO handle for double

            // TableView excepts ObservableList
            // creates ObservableList from Category Assignments
            ObservableList<AssignmentInterface> assignments = FXCollections.observableArrayList();
            assignments.setAll(category.getAssignments());
            cTableView.setItems(assignments);
            cTableView.getColumns().addAll(nameColumn, gradeColumn, potentialGradeColumn);

            categoryTables.add(cTableView);
        }

    }


    @Override
    public void handle(ActionEvent event) {
    }

    static class CTableView extends TableView<AssignmentInterface> {
        CategoryInterface tableCategory;

        CTableView(CategoryInterface tableCategory) {
            this.tableCategory = tableCategory;
        }
    }
}
