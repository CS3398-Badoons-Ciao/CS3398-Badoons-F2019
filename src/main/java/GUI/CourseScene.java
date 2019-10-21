package GUI;

import Factory.AssignmentFactory;
import Interfaces.AssignmentInterface;
import Interfaces.CategoryInterface;
import Interfaces.Listener;
import Interfaces.Publisher;
import Model.Category;
import Model.Course;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.DoubleStringConverter;

import java.util.ArrayList;
import java.util.List;

public class CourseScene implements Listener, EventHandler<ActionEvent> {
    private Course course;
    private Scene scene;
    private VBox sceneLayout = new VBox();
    private MenuBar menuBar = new MenuBar();
    private HBox titleLayout = new HBox();
    private HBox assignmentMenuLayout = new HBox();
    private ComboBox assignmentMenuDropDown = new ComboBox();
    private HBox categoryMenu =  new HBox();
    private VBox categoryTablesLayout = new VBox();

    private List<CategoryTable> categoryTables = new ArrayList<>();
    private ObservableList<String> dropDownListCategories;

    // potential undo feature
    // stack would need assignment & category for entry - perhaps make class
    // private Stack<AssignmentInterface> deletedAssignments = new Stack<AssignmentInterface>();

    /**
     * sets course and builds scene
      * @param course course for scene
     */
    public CourseScene(Course course) {
        this.course = course;
        register(course);
        buildScene();
    }

    public Scene getScene() {return scene;}

    @Override
    public void update(Publisher model) {
        //buildScene();
    }

    @Override
    public void register(Publisher publisher) {
        publisher.addListener(this);
    }

    private void buildMenu() {
        Menu menu = new Menu("Menu");
        MenuItem backItem = new MenuItem("Back");
        backItem.setOnAction(event -> {

        });

        menu.getItems().add(backItem);
        menuBar = new MenuBar();
        menuBar.getMenus().add(menu);
    }

    private void buildTitle() {
        TextField sceneTitle = new TextField(course.getName());
        sceneTitle.setStyle(
                "-fx-text-box-border: transparent; " +
                "-fx-background-color: transparent;" +
                "-fx-font-weight: bold; " +
                "-fx-font-size: 24pt;");

        sceneTitle.setFocusTraversable(false); // disables focus on start
        sceneTitle.setAlignment(Pos.CENTER);
        sceneTitle.focusedProperty().addListener(
                (ObservableValue<? extends Boolean> observable, Boolean lostFocus, Boolean gainFocus) -> {
            if (lostFocus) {
                course.setName(sceneTitle.getText());
            }
        });

        titleLayout.getChildren().add(sceneTitle);
        titleLayout.setAlignment(Pos.CENTER);
    }

    // builds 'add assignment'feature
    private void buildAssignmentMenu() {
        dropDownListCategories = FXCollections.observableArrayList();
        for (CategoryTable categoryTable : categoryTables) {
            dropDownListCategories.add(categoryTable.category.getName());
        }

        assignmentMenuDropDown = new ComboBox(dropDownListCategories);
        assignmentMenuDropDown.setOnAction(event -> {
            // TODO highlight table to add course to
        });

        assignmentMenuDropDown.setPromptText("Category");

        final TextField addCourseName = new TextField();
        addCourseName.setPromptText("Course Name");

        final TextField addCurrentGrade = new TextField();
        addCurrentGrade.setPromptText("Current Grade");

        final TextField addPotentialGrade = new TextField();
        addPotentialGrade.setPromptText("Potential Grade");

        final Button addAssignmentBtn = new Button("+");
        addAssignmentBtn.setOnAction(addEvent -> {
            for (CategoryTable categoryTable : categoryTables) {
                if (categoryTable.category.getName() == assignmentMenuDropDown.getValue()) {
                    try {
                        String courseName = addCourseName.getText();
                        double currentGrade = Double.parseDouble(addCurrentGrade.getText());
                        double potentialGrade = Double.parseDouble(addPotentialGrade.getText());

                        // adds assignment to model
                        categoryTable.category.addAssignment(AssignmentFactory.createAssignment(
                                courseName, currentGrade, potentialGrade));

                        // adds assignment to table
                        categoryTable.getItems().add(AssignmentFactory.createAssignment(
                                courseName, currentGrade, potentialGrade));

                    } catch(Exception exception) {
                        System.out.println(exception.getMessage());
                    } // TODO handle pop-up bad format
                }
            }

            addCourseName.clear();
            addCurrentGrade.clear();
            addPotentialGrade.clear();
        });

        assignmentMenuLayout.getChildren().addAll(
                assignmentMenuDropDown, addCourseName, addCurrentGrade, addPotentialGrade, addAssignmentBtn);
        assignmentMenuLayout.setSpacing(3);
    }

    // builds category menu
    private void buildCategoryMenu() {
        final TextField addCategoryName = new TextField();
        addCategoryName.setPromptText("Category Name");

        final TextField addCategoryWeight = new TextField();
        addCategoryWeight.setPromptText("Category Weight");

        final Button addCategoryBtn = new Button("+");
        addCategoryBtn.setOnAction(event -> {
            String categoryNameText = addCategoryName.getText();
            double categoryWeight = Double.valueOf(addCategoryWeight.getText());

            Category newCategory = new Category(categoryNameText, categoryWeight);
            course.addCategory(newCategory);
            CategoryTable newCategoryTable = buildCategoryTable(newCategory);
            categoryTables.add(newCategoryTable);
            categoryTablesLayout.getChildren().add(newCategoryTable.categoryTableLayout);
            dropDownListCategories.add(newCategory.getName());

            addCategoryName.clear();
            addCategoryWeight.clear();
        });

        categoryMenu.getChildren().addAll(addCategoryName, addCategoryWeight, addCategoryBtn);
    }

    // builds single category table
    private CategoryTable buildCategoryTable(Category category) {
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
                /* END DEBUG */
        });

        // creates a grade column
        TableColumn<AssignmentInterface, Double> gradeColumn = new TableColumn<>("Grade");
        gradeColumn.setMinWidth(100);
        gradeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("currentGrade"));
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
        TableColumn<AssignmentInterface, Double> potentialGradeColumn = new TableColumn<>("Potential Grade");
        potentialGradeColumn.setMinWidth(100);
        potentialGradeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        potentialGradeColumn.setCellValueFactory(new PropertyValueFactory<>("potentialGrade"));
        potentialGradeColumn.setOnEditCommit( event -> {
            try {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).
                        setPotentialGrade(event.getNewValue());
            } catch(Exception e) {
                // TODO catch specific exception related to bad input
                // this updates table with data model if exception is caught
                categoryTable.refresh();
            }
        });

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

        categoryTable.getColumns().addAll(nameColumn, gradeColumn, potentialGradeColumn);
        categoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // removes blank rows
        categoryTable.setFixedCellSize(28);
        categoryTable.prefHeightProperty().bind(
                categoryTable.fixedCellSizeProperty().multiply(Bindings.size(categoryTable.getItems()).add(1.01)));
        categoryTable.minHeightProperty().bind(categoryTable.prefHeightProperty());
        categoryTable.maxHeightProperty().bind(categoryTable.prefHeightProperty());

        // removes lines between rows
        categoryTable.setStyle("-fx-table-cell-border-color: transparent; -fx-focus-color: transparent;");

        //categoryTable.lookup("TableHeaderRow");
        return categoryTable;

    }

    // builds 'category tables' feature from categories
    private void buildCategoryTables() {
        ArrayList<Category> categories = course.getCategories();

        for (Category category : categories) {
            CategoryTable newCategoryTable = buildCategoryTable(category);
            categoryTables.add(newCategoryTable);
        }
    }

    // adds layouts to scene
    private void buildScene() {
        buildMenu();
        buildTitle();
        buildCategoryMenu();
        buildCategoryTables();
        buildAssignmentMenu();

        sceneLayout.setFillWidth(true);
        sceneLayout.setSpacing(20);
        sceneLayout.setPadding(new Insets(5, 35, 35, 35));

        // adds components to scene layout
        sceneLayout.getChildren().addAll
                (menuBar, titleLayout, assignmentMenuLayout, categoryMenu);

        for (CategoryTable categoryTable : categoryTables) {
            categoryTablesLayout.getChildren().add(categoryTable.categoryTableLayout);
        }

        sceneLayout.getChildren().add(categoryTablesLayout);

        // adds scrollbar for sceneLayout
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(sceneLayout);
        scrollPane.setPannable(true);
        scrollPane.fitToWidthProperty().set(true);
        scrollPane.fitToHeightProperty().set(true);

        scene = new Scene(scrollPane);
    }

    @Override
    public void handle(ActionEvent event) {
    }

    class CategoryTable extends TableView<AssignmentInterface> {
        CategoryInterface category;
        ObservableList<AssignmentInterface> assignments = FXCollections.observableArrayList();
        HBox titleLayout = new HBox();
        TextField title;
        VBox categoryTableLayout = new VBox();


        CategoryTable(CategoryInterface category) {
            this.category = category;
            this.assignments.setAll(category.getAssignments());
            setItems(assignments);
            title = new TextField(category.getName());
            title.focusedProperty().addListener(
                    (ObservableValue<? extends Boolean> observable, Boolean lostFocus, Boolean gainFocus) -> {
                        if (lostFocus) {
                            category.setName(title.getText());
                            System.out.println("Focus Lost");
                        }
                    });

            title.setStyle(
                    "-fx-text-box-border: transparent; " +
                    "-fx-background-color: transparent;" +
                    "-fx-font-weight: bold; " +
                    "-fx-font-size: 14pt; ");

            titleLayout.getChildren().add(title);

            categoryTableLayout.setPadding(new Insets(8,0,8,0));
            categoryTableLayout.getChildren().add(titleLayout);
            categoryTableLayout.getChildren().add(this);
        }
    }
}
