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
    CategoryCalculatorInterface categoryCalculator;

    // potential undo feature
    // stack would need assignment & category for entry - perhaps make class
    // private Stack<AssignmentInterface> deletedAssignments = new Stack<AssignmentInterface>();

    /**
     * sets course and builds scene
      * @param course course for scene
     */
    public CourseScene(Course course, CategoryCalculatorInterface categoryCalculator) {
        this.course = course;
        this.categoryCalculator = categoryCalculator;
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
            CategoryTable newCategoryTable = new CategoryTable(newCategory, categoryCalculator);
            categoryTables.add(newCategoryTable);
            categoryTablesLayout.getChildren().add(newCategoryTable.categoryTableLayout);
            dropDownListCategories.add(newCategory.getName());

            addCategoryName.clear();
            addCategoryWeight.clear();
        });

        categoryMenu.getChildren().addAll(addCategoryName, addCategoryWeight, addCategoryBtn);
    }

    // builds 'category tables' feature from categories
    private void buildCategories() {
        for (Category category : course.getCategories()) {
            CategoryTable categoryTable = new CategoryTable(category, categoryCalculator);
            categoryTables.add(categoryTable);
        }
    }

    // adds layouts to scene
    private void buildScene() {
        buildMenu();
        buildTitle();
        buildCategoryMenu();
        buildCategories();
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


}
