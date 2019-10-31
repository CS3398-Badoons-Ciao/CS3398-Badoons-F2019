package GUI;

import Factory.AssignmentFactory;
import Interfaces.*;
import Model.Category;
import Model.Course;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Scene for displaying a Course
 */
public class CourseScene implements Listener, EventHandler<ActionEvent> {
    /** The previous scene */
    private Scene previousScene;

    /** Scene for Stage */
    private Scene scene;

    /** Course to display **/
    private Course course;

    /** Save feature */
    private SaveUserInterface userSaver;

    /** 'scroll bar' feature */
    private ScrollPane scrollPane = new ScrollPane();

    /** root layout */
    private VBox sceneLayout = new VBox();

    /** 'Menu Bar' feature */
    private MenuBar menuBar = new MenuBar();

    /** root layout for scene title */
    private HBox titleLayout = new HBox();

    /** root layout for 'add assignment' feature */
    private BoxSplitLayout addAssignmentLayout = new BoxSplitLayout();

    /** child Node for 'add assignment' feature */
    private ComboBox<String> addAssignmentDropDown = new ComboBox<>();

    /** root layout for 'add category' feature */
    private BoxSplitLayout addCategoryLayout =  new BoxSplitLayout();

    /** root layout for 'remove category' feature */
    private BoxSplitLayout removeCategoryLayout = new BoxSplitLayout();

    /** child Node for 'remove category' feature */
    private ComboBox<String> removeCategoryDropDown = new ComboBox<>();

    /** root layout for 'category tables' feature */
    private VBox categoryTablesLayout = new VBox();

    /** List of CategoryTable, one-to-one relationship for all categories in Course */
    private List<CategoryTable> categoryTables = new ArrayList<>();

    /** ObservableList stores all Course Category for use in any drop down list */
    private ObservableList<String> dropDownListCategories;

    /** a grade calculator injected dependency */
    private CategoryCalculatorInterface categoryCalculator;

    // potential undo feature for assignments, stack could hold maps, key = assignment, value = category)
    // private Stack<Map> deletedAssignments = new Stack<AssignmentInterface>();

    /**
     * sets course and builds scene
      * @param course course for scene
     */
    public CourseScene(Scene previousScene,
                       Course course,
                       CategoryCalculatorInterface categoryCalculator,
                       SaveUserInterface userSaver) {

        this.previousScene = previousScene;
        this.course = course;
        this.categoryCalculator = categoryCalculator;
        this.userSaver = userSaver;
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
            ((Stage)scene.getWindow()).setScene(previousScene);
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

    private void buildAddAssignmentLayout() {
        addAssignmentLayout.headLabel.setText("Add Assignment");

        // creates addAssignmentBodyLayout
        if (dropDownListCategories == null) {
            dropDownListCategories = FXCollections.observableArrayList();
            for (CategoryInterface category : course.getCategories()) {
                dropDownListCategories.add(category.getName());
            }
        }

        addAssignmentDropDown = new ComboBox<>(dropDownListCategories);
        addAssignmentDropDown.setOnAction(event -> {
            // TODO highlight table to add course to
        });

        addAssignmentDropDown.setPromptText("Category");

        final TextField addCourseName = new TextField();
        addCourseName.setPromptText("Course Name");

        final TextField addCurrentGrade = new TextField();
        addCurrentGrade.setPromptText("Current Grade");

        final TextField addPotentialGrade = new TextField();
        addPotentialGrade.setPromptText("Potential Grade");

        final Button addAssignmentBtn = new Button("+");
        addAssignmentBtn.setOnAction(addEvent -> {
            for (CategoryTable categoryTable : categoryTables) {
                if (categoryTable.category.getName().equals(addAssignmentDropDown.getValue())) {
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

                        String updatedCategoryGrade =
                                String.valueOf(categoryCalculator.getCategoryGrade(categoryTable.category.getAssignments()));

                        categoryTable.gradeLabel.setText(updatedCategoryGrade);

                    } catch(Exception exception) {
                        System.out.println(exception.getMessage());
                    } // TODO handle pop-up bad format
                }
            }

            addCourseName.clear();
            addCurrentGrade.clear();
            addPotentialGrade.clear();
        });

        addAssignmentLayout.bodyLayout.getChildren().addAll(
                addAssignmentDropDown, addCourseName, addCurrentGrade, addPotentialGrade, addAssignmentBtn);
    }

    private void buildAddCategoryLayout() {
        addCategoryLayout.headLabel.setText("Add Category");

        final TextField addCategoryName = new TextField();
        addCategoryName.setPromptText("Category Name");

        final TextField addCategoryWeight = new TextField();
        addCategoryWeight.setPromptText("Category Weight");

        final Button addCategoryBtn = new Button("+");

        /*
         * handles button press for adding a category
         */
        addCategoryBtn.setOnAction(event -> {
            String categoryNameText = addCategoryName.getText();
            double categoryWeight = Double.valueOf(addCategoryWeight.getText());

            // creates new Category
            Category newCategory = new Category(categoryNameText, categoryWeight);

            // adds Category to Course
            course.addCategory(newCategory);

            // builds new CategoryTable Node for Scene
            CategoryTable newCategoryTable = new CategoryTable(newCategory, categoryCalculator);

            // adds Node to collection of all CategoryTable Node
            categoryTables.add(newCategoryTable);

            // adds (appends) the child CategoryTableLayout to parent CategoryTableslayout
            categoryTablesLayout.getChildren().add(newCategoryTable.categoryTableLayout);

            // adds Category to Observable drop down list; multiple ComboBox listeners to list.
            dropDownListCategories.add(newCategory.getName());

            addCategoryName.clear();
            addCategoryWeight.clear();
        });

        addCategoryLayout.bodyLayout.getChildren().addAll(addCategoryName, addCategoryWeight, addCategoryBtn);
    }

    private void buildCategoryRemoveLayout() {
        removeCategoryLayout.headLabel.setText("Remove Category");

        if (dropDownListCategories == null) {
            dropDownListCategories = FXCollections.observableArrayList();
            for (CategoryInterface category : course.getCategories()) {
                dropDownListCategories.add(category.getName());
            }
        }

        removeCategoryDropDown = new ComboBox<>(dropDownListCategories);
        removeCategoryDropDown.setPromptText("Category");
        removeCategoryDropDown.setOnAction(event -> {
        });

        final Button removeCategoryBtn = new Button("-");

        /*
         * handles button for removing a Category
         */
        removeCategoryBtn.setOnAction(event -> {
            CategoryTable categoryTableToRemove = null;
            for (CategoryTable categoryTable : categoryTables) {
                if (categoryTable.category.getName().equals(removeCategoryDropDown.getValue())) {
                    categoryTableToRemove = categoryTable;
                    break;
                }
            }

            if (categoryTableToRemove != null) {
                // removes Category from Course
                course.removeCategory(categoryTableToRemove.category);

                // removes Category from ObservableList for representing categories ComboBox
                boolean found = dropDownListCategories.remove(categoryTableToRemove.category.getName());

                // removes Category from list of CategoryTable
                categoryTables.remove(categoryTableToRemove);

                // removes CategoryTable from Scene
                categoryTablesLayout.getChildren().remove(categoryTableToRemove);
                categoryTablesLayout.getChildren().remove(categoryTableToRemove.categoryTableLayout);
                }
        });

        removeCategoryLayout.bodyLayout.getChildren().addAll(removeCategoryDropDown, removeCategoryBtn);
    }


    private void buildCategories() {
        for (Category category : course.getCategories()) {
            CategoryTable categoryTable = new CategoryTable(category, categoryCalculator);
            categoryTables.add(categoryTable);
        }
    }

    /**
     * builds and adds Nodes to Scene
     */
    private void buildScene() {
        buildMenu();
        buildTitle();
        buildAddCategoryLayout();
        buildCategoryRemoveLayout();
        buildCategories();
        buildAddAssignmentLayout();

        sceneLayout.setFillWidth(true);
        sceneLayout.setSpacing(20);
        sceneLayout.setPadding(new Insets(5, 35, 35, 35));

        sceneLayout.getChildren().addAll
                (menuBar, titleLayout, addCategoryLayout, removeCategoryLayout, addAssignmentLayout);

        for (CategoryTable categoryTable : categoryTables) {
            categoryTablesLayout.getChildren().add(categoryTable.categoryTableLayout);
        }

        sceneLayout.getChildren().add(categoryTablesLayout);

        scrollPane.setContent(sceneLayout);
        scrollPane.setPannable(true);
        scrollPane.fitToWidthProperty().set(true);
        scrollPane.fitToHeightProperty().set(true);

        scene = new Scene(scrollPane);
    }

    @Override
    public void handle(ActionEvent event) {
    }

}
