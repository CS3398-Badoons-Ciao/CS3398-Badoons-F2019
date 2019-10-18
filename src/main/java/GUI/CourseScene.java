package GUI;

import Factory.AssignmentFactory;
import Interfaces.AssignmentInterface;
import Interfaces.CategoryInterface;
import Interfaces.Listener;
import Interfaces.Publisher;
import Model.Category;
import Model.Course;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Light;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CourseScene implements Listener, EventHandler<ActionEvent> {
    private Course course;
    private Scene scene;
    private VBox sceneLayout = new VBox();
    private MenuBar menuBar = new MenuBar();
    private HBox titleLayout = new HBox();
    private HBox assignmentAddLayout = new HBox();
    private ComboBox assignmentAddDropDown = new ComboBox();
    private List<CategoryTable> categoryTables = new ArrayList<>();

    //holds assignments that have been deleted by user on this scene for undo feature
    // private Stack<AssignmentInterface> deletedAssignments = new Stack<AssignmentInterface>();

    /**
     * sets course and builds scene
      * @param course
     */
    public CourseScene(Course course) {
        this.course = course;
        register(course);
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

    // builds full scene and scene layout
    private void buildScene() {
        buildMenu();
        buildTitle();
        buildCategoryTables();
        buildAddAssignment();

        // configures scene layout
        sceneLayout.setFillWidth(true);
        sceneLayout.setSpacing(20);
        sceneLayout.setPadding(new Insets(5, 35, 5, 35));

        // adds components to scene layout
        sceneLayout.getChildren().addAll(menuBar);
        sceneLayout.getChildren().add(titleLayout);

        for (CategoryTable categoryTable : categoryTables) {
            Label categoryLabel = new Label(categoryTable.category.getName());
            categoryLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");

            VBox categoryTableLayout = new VBox();
            categoryTableLayout.setPadding(new Insets(8,0,8,0));
            categoryTableLayout.getChildren().add(categoryLabel);
            categoryTableLayout.getChildren().add(categoryTable);

            sceneLayout.getChildren().add(categoryTableLayout);
        }

        sceneLayout.getChildren().addAll(assignmentAddLayout);

        // adds scrollbar for sceneLayout
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(sceneLayout);
        scrollPane.setPannable(true);
        scrollPane.fitToWidthProperty().set(true);
        scrollPane.fitToHeightProperty().set(true);

        scene = new Scene(scrollPane);
    }

    // builds 'category tables' feature
    private void buildCategoryTables() {
        ArrayList<Category> categories = course.getCategories();

        for (Category category : categories) {
            CategoryTable categoryTable = new CategoryTable(category);
            categoryTable.setEditable(true);

            // creates a name column
            TableColumn<AssignmentInterface, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setMinWidth(100);
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name")); // property must match object
            nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            nameColumn.setOnEditCommit( event -> {
                // TODO Ask Model developer to throw exceptions with bad input
                try {
                    // On table edit by user, table value (display-only) is updated
                    // must set new value in observable list which is observed by table
                    // this also updates the model
                    event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
                } catch(Exception e) {
                    // TODO catch specific exception related to bad input
                    // this updates table with data model if exception is caught
                    categoryTable.refresh();
                }

                /* DEBUG
                System.out.println("Assignments in Observable List: ");
                for (AssignmentInterface a : event.getTableView().getItems()) {
                    System.out.println(a.getName());
                }
                System.out.println("Assignments in category (Model): ");
                                for (AssignmentInterface a : category.getAssignments()) {
                    System.out.println(a.getName());
                }
                END DEBUG */
            });

            // creates a grade column
            TableColumn<AssignmentInterface, Double> gradeColumn = new TableColumn<>("Grade");
            gradeColumn.setMinWidth(100);
            gradeColumn.setCellValueFactory(new PropertyValueFactory<>("currentGrade"));
            //gradeColumn.setCellFactory(TextFieldTableCell.forTableColumn()); // TODO fix
            // TODO handle for double
            gradeColumn.setOnEditCommit( event -> {
                try {
                    event.getTableView().getItems().get(event.getTablePosition().getRow()).
                            setCurrentGrade(event.getNewValue());
                } catch(Exception e) {
                    // TODO catch specific exception related to bad input
                    // this updates table with data model if exception is caught
                    categoryTable.refresh();
                }
            });

            // creates a potential grade column
            TableColumn<AssignmentInterface, String> potentialGradeColumn = new TableColumn<>("Potential Grade");
            potentialGradeColumn.setMinWidth(100);
            potentialGradeColumn.setCellValueFactory(new PropertyValueFactory<>("potentialGrade"));
            // TODO handle for double

            // TableView accepts ObservableList
            // creates ObservableList from Category Assignments
            ObservableList<AssignmentInterface> assignments = FXCollections.observableArrayList();
            assignments.setAll(category.getAssignments());
            categoryTable.setItems(assignments);
            categoryTable.getColumns().addAll(nameColumn, gradeColumn, potentialGradeColumn);

            categoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            // delete assignment with delete key feature
            categoryTable.setOnKeyPressed(keyEvent -> {
                AssignmentInterface selectedAssignment = categoryTable.getSelectionModel().getSelectedItem();
                if (selectedAssignment !=  null) {
                    if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                        // adds removed assignment to stack for undo feature
                        // must also store category
                        // deletedAssignments.push(selectedAssignment);

                        // remove assignment from category (model)
                        categoryTable.category.removeAssignment(selectedAssignment.getName());

                        // delete from table
                        categoryTable.getItems().remove(selectedAssignment);

                    }
                }
            });

            // removes blank rows
            categoryTable.setFixedCellSize(28);
            categoryTable.prefHeightProperty().bind(categoryTable.fixedCellSizeProperty().multiply(Bindings.size(categoryTable.getItems()).add(1.01)));
            categoryTable.minHeightProperty().bind(categoryTable.prefHeightProperty());
            categoryTable.maxHeightProperty().bind(categoryTable.prefHeightProperty());

            // removes lines between rows
            categoryTable.setStyle("-fx-table-cell-border-color: transparent; -fx-focus-color: transparent;");

            //categoryTable.lookup("TableHeaderRow");

            categoryTables.add(categoryTable);
        }

    }

    // builds 'add assignment'feature
    void buildAddAssignment() {
        ObservableList<String> dropDownListCategories = FXCollections.observableArrayList();
        for (CategoryTable categoryTable : categoryTables) {
            dropDownListCategories.add(categoryTable.category.getName());
        }

        assignmentAddDropDown = new ComboBox(dropDownListCategories);
        assignmentAddDropDown.setOnAction(event -> {
            // TODO highlight table to add course to
        });

        assignmentAddDropDown.setPromptText("Category");

        final TextField addCourseName = new TextField();
        addCourseName.setPromptText("Course Name");

        final TextField addCurrentGrade = new TextField();
        addCurrentGrade.setPromptText("Current Grade");

        final TextField addPotentialGrade = new TextField();
        addPotentialGrade.setPromptText("Potential Grade");

        final Button addAssignmentBtn = new Button("+");
        addAssignmentBtn.setOnAction(addEvent -> {
            for (CategoryTable categoryTable : categoryTables) {
                if (categoryTable.category.getName() == assignmentAddDropDown.getValue()) {
                    try {
                        String courseName = addCourseName.getText();
                        double currentGrade = Double.parseDouble(addCurrentGrade.getText());
                        double potentialGrade = Double.parseDouble(addPotentialGrade.getText());

                        // adds assignment to model
                        categoryTable.category.addAssignment(AssignmentFactory.createAssignment(
                            courseName, potentialGrade, currentGrade));

                        // adds assignment to table
                        categoryTable.getItems().add(AssignmentFactory.createAssignment(
                                courseName, potentialGrade, currentGrade));

                    } catch(Exception exception) {
                        System.out.println(exception.getMessage()); // TODO delete
                    } // TODO handle pop-up bad format
                }
            }

            addCourseName.clear();
            addCurrentGrade.clear();
            addPotentialGrade.clear();
        });

        assignmentAddLayout.getChildren().addAll(
                assignmentAddDropDown, addCourseName, addCurrentGrade, addPotentialGrade, addAssignmentBtn);
        assignmentAddLayout.setSpacing(3);
    }

    void buildMenu() {
        Menu menu = new Menu("Menu");
        MenuItem backItem = new MenuItem("Back");
        backItem.setOnAction(event -> {

        });

        menu.getItems().add(backItem);

        menuBar = new MenuBar();
        menuBar.getMenus().add(menu);

    }

    void buildTitle() {
        final Label sceneTitle = new Label(course.getName());
        sceneTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 20pt;");

        titleLayout.getChildren().add(sceneTitle);
        titleLayout.setAlignment(Pos.CENTER);
    }

    @Override
    public void handle(ActionEvent event) {
    }

    class CategoryTable extends TableView<AssignmentInterface> {
        CategoryInterface category;

        CategoryTable(CategoryInterface category) {
            this.category = category;
        }
    }
}
