package GUI;

import Factory.AssignmentFactory;
import Interfaces.*;
import Model.Category;
import Model.Course;
import Model.Model;
import Exception.*;
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
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Scene for displaying a Course
 */
// TODO remove Listener
public class CourseScene {
    /** The primary Stage */
    private Stage primaryStage;

    /** Course Scene for Stage */
    private Scene scene;

    /** Course to display **/
    private Course course;

    /** User Model */
    private Model model;

    /** 'scroll bar' feature */
    private ScrollPane scrollPane = new ScrollPane();

    /** root layout */
    private BorderPane sceneLayout = new BorderPane();

    /** holds menu bar and course name */
    private VBox sceneLayoutTop = new VBox();

    /** holds edit boxes */
    private VBox sceneLayoutCenter = new VBox();

    /** 'Menu Bar' feature */
    private MenuBar menuBar = new MenuBar();

    /** root layout for scene title */
    private VBox titleLayout = new VBox();

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

    /** scrollpane for categoryTablesLayout */
    private ScrollPane tableScrollPane = new ScrollPane();

    /** List of CategoryTable, one-to-one relationship for all categories in Course */
    private List<CategoryTable> categoryTables = new ArrayList<>();

    /** ObservableList stores Course Category names.
     *  Must be updated when any Course Category Name changes by calling updateCategoryNames()
     */
    private ObservableList<String> dropDownListCategories;

    /** a grade calculator injected dependency */
    private CategoryCalculatorInterface categoryCalculator;

    /** layout for course grade */
    HBox courseGradeLayout = new HBox();

    /** for course grade */
    Label courseGrade= new Label();

    /** formatter for displaying doubles */
    NumberFormat doubleFormatter = new DecimalFormat("#0.00");

    String courseNameStyle =
            "-fx-text-box-border: transparent; " +
            "-fx-background-color: transparent;" +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 24pt;";

    String courseNameStyleOnFocus =
            "-fx-font-weight: bold; " +
            "-fx-font-size: 24pt;";

    String courseGradeStyle = "-fx-font-weight: bold; "
            + "-fx-font-size: 16pt;";

    /**
     * sets course and builds scene
      * @param course course for scene
     */
    public CourseScene(Stage primaryStage,
                       Course course,
                       CategoryCalculatorInterface categoryCalculator,
                       Model model) {

        this.primaryStage = primaryStage;
        this.course = course;
        this.categoryCalculator = categoryCalculator;
        this.model = model;

        buildScene();
    }

    public Scene getScene() {return scene;}

    private void buildMenu() {
        Menu fileMenu = new Menu("File");

        MenuItem saveItem = new MenuItem("Save");
        saveItem.setOnAction(event -> {
            model.saveUser();
        });

        MenuItem logOutItem = new MenuItem("Log Out");
        logOutItem.setOnAction(event -> {
            try {
                primaryStage.setScene((new LogIn(model, primaryStage)).getScene());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        MenuItem coursesItem = new MenuItem("Back");
        coursesItem.setOnAction(event -> {
            primaryStage.setScene((new CourseOverview(model, primaryStage)).getScene());
        });

        MenuItem helpItem = new MenuItem("Help");
        helpItem.setOnAction(event -> {
            Popup popup = new Popup();

            final String helpText = "Welcome to the course scene. Here you can find and edit\n" +
                                    "all of the information relating to this course. To start,\n" +
                                    "add a new category by giving it a name and a weight.\n" +
                                    "For example, if quizzes are worth 20% of the course's grade\n" +
                                    "then you would create a category called \"Quizzes\" with a\n" +
                                    "weight of \".20\". Once you've added all of the categories, \n" +
                                    "you can add assignments to the category. For example if you \n" +
                                    "got a 90 on a test with 100 total points, you would create an\n" +
                                    "assignment named \"Test One\" with 90 as the current score and\n" +
                                    "100 as the potential score.";


            Label label = new Label(helpText);

            Button exitButton = new Button("Close Popup");
            exitButton.setOnAction(addEvent -> {
                popup.hide();
            });

            BoxSplitLayout popupLayout = new BoxSplitLayout();

            //centers popup
            popup.centerOnScreen();

            //define the popups background color
            //popupLayout.setStyle("-fx-background-color: #4287f5;");

            popupLayout.bodyLayout.getChildren().addAll(label, exitButton);

            popup.getContent().add(popupLayout);
            popup.show(primaryStage);
        });

        fileMenu.getItems().addAll(coursesItem, saveItem, logOutItem, helpItem);

        menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu);
    }

    private void buildTitle() {
        TextField sceneTitle = new TextField(course.getName());
        sceneTitle.setStyle(courseNameStyle);

        sceneTitle.setFocusTraversable(false);
        sceneTitle.setAlignment(Pos.CENTER);
        sceneTitle.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean lostFocus, Boolean gainFocus) -> {
            if (lostFocus) {
                sceneTitle.setStyle(courseNameStyle);
                sceneTitle.setText(course.getName());
            }
            else if (gainFocus) {
                sceneTitle.setStyle(courseNameStyleOnFocus);
            }
        });

        sceneTitle.setOnAction(event -> {
            sceneTitle.setStyle(courseNameStyle);

            try {
                model.user.changeCourseName(course, sceneTitle.getText());
                sceneTitle.setText(course.getName());
            } catch (DuplicateNameException d) {
                AlertPopUp.alert(d.title, d.header, d.toString());
            }

            titleLayout.requestFocus();
        });

        Label courseGradeLabel = new Label("Course Grade: ");
        courseGradeLabel.setStyle(courseGradeStyle);

        courseGrade.setText(doubleFormatter.format(categoryCalculator.getCourseGrade(course)));
        courseGrade.setStyle(courseGradeStyle);

        courseGradeLayout.getChildren().addAll(courseGradeLabel, courseGrade);
        courseGradeLayout.setAlignment(Pos.CENTER);

        titleLayout.getChildren().addAll(sceneTitle, courseGradeLayout);
        titleLayout.setSpacing(5);
        titleLayout.setPadding(new Insets(25,0,25,0));
    }

    private void buildAddAssignmentLayout() {
        addAssignmentLayout.headLabel.setText("Add Assignment");

        updateCategoryNames();

        // creates addAssignmentBodyLayout
        addAssignmentDropDown = new ComboBox<>(dropDownListCategories);
        addAssignmentDropDown.setOnAction(event -> {
            // TODO highlight table to add course to
        });

        addAssignmentDropDown.setPromptText("Category");

        final TextField addCourseName = new TextField();
        addCourseName.setPromptText("Assignment Name");

        final TextField addCurrentGrade = new TextField();
        addCurrentGrade.setPromptText("Current Grade");

        final TextField addPotentialGrade = new TextField();
        addPotentialGrade.setPromptText("Potential Grade");

        final Button addAssignmentBtn = new Button("+");
        final Tooltip addAssignmentBtnTooltip = new Tooltip("Adds a new assignment");
        Tooltip.install(addAssignmentBtn, addAssignmentBtnTooltip);
        addAssignmentBtn.setOnAction(addEvent -> {
            for (CategoryTable categoryTable : categoryTables) {
                if (categoryTable.category.getName().equals(addAssignmentDropDown.getValue())) {
                    try {
                        String courseName = addCourseName.getText();
                        double currentGrade = Double.parseDouble(addCurrentGrade.getText());
                        double potentialGrade = Double.parseDouble(addPotentialGrade.getText());

                        // creates new assignment and adds to model
                        categoryTable.category.addAssignment(AssignmentFactory.createAssignment(
                                courseName, currentGrade, potentialGrade));

                        // replace table items (assignments) with model assignments
                        categoryTable.getItems().setAll(categoryTable.category.getAssignments());

                        Double updatedCategoryGrade = categoryCalculator.getCategoryGrade(categoryTable.category.getAssignments());
                        categoryTable.gradeLabel.setText(doubleFormatter.format(updatedCategoryGrade));

                        updateCourseGrade();
                    }
                    catch (DuplicateNameException d) {
                        AlertPopUp.alert(d.title, d.header, d.toString());
                    }
                    catch(Exception exception) {
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
        final Tooltip addCategoryBtnTooltip = new Tooltip("Adds a new category (ex: tests, homework, etc.)");
        Tooltip.install(addCategoryBtn, addCategoryBtnTooltip);

        /*
         * handles button for adding Category
         */
        addCategoryBtn.setOnAction(event -> {
            try {
                String categoryNameText = addCategoryName.getText();
                double categoryWeight = Double.valueOf(addCategoryWeight.getText());

                // creates new Category
                Category newCategory = new Category(categoryNameText, categoryWeight);

                // adds Category to Course
                course.addCategory(newCategory);


                // builds new CategoryTable Node for Scene
                CategoryTable newCategoryTable = new CategoryTable(course, newCategory, categoryCalculator, this);

                // adds Node to collection of all CategoryTable Node
                categoryTables.add(newCategoryTable);

                // adds (appends) the child CategoryTableLayout to parent CategoryTables layout
                categoryTablesLayout.getChildren().add(newCategoryTable.categoryTableLayout);

                // adds Category to Observable drop down list; multiple ComboBox listeners to list.
                dropDownListCategories.add(newCategory.getName());

                updateCourseGrade();

                addCategoryName.clear();
                addCategoryWeight.clear();
            }
            catch (DuplicateNameException d) {
                AlertPopUp.alert(d.title, d.header, d.toString());
            }
        });

        addCategoryLayout.bodyLayout.getChildren().addAll(addCategoryName, addCategoryWeight, addCategoryBtn);
    }

    private void buildCategoryRemoveLayout() {
        removeCategoryLayout.headLabel.setText("Remove Category");

        updateCategoryNames();

        removeCategoryDropDown = new ComboBox<>(dropDownListCategories);
        removeCategoryDropDown.setPromptText("Category");
        removeCategoryDropDown.setOnAction(event -> {
        });

        final Button removeCategoryBtn = new Button("-");
        final Tooltip removeCategoryBtnTooltip = new Tooltip("Removes the selected category");
        Tooltip.install(removeCategoryBtn, removeCategoryBtnTooltip);

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

                // update Course Category Name's ObservableList
                updateCategoryNames();

                // removes Category from list of CategoryTable's
                categoryTables.remove(categoryTableToRemove);

                // removes CategoryTable from Scene
                categoryTablesLayout.getChildren().remove(categoryTableToRemove);
                categoryTablesLayout.getChildren().remove(categoryTableToRemove.categoryTableLayout);

                updateCourseGrade();
            }
        });

        removeCategoryLayout.bodyLayout.getChildren().addAll(removeCategoryDropDown, removeCategoryBtn);
    }

    private void buildCategories() {
        for (Category category : course.getCategories()) {
            CategoryTable categoryTable = new CategoryTable(course, category, categoryCalculator, this);
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

        sceneLayoutTop.getChildren().addAll(menuBar, titleLayout);

        sceneLayoutCenter.getChildren().addAll(addAssignmentLayout, addCategoryLayout, removeCategoryLayout);
        sceneLayoutCenter.setPadding(new Insets(90, 50, 35, 100));

        sceneLayout.setTop(sceneLayoutTop);
        sceneLayout.setCenter(sceneLayoutCenter);
        sceneLayout.setPadding(new Insets(50, 50, 50, 50));

        Label categoryTablesLabel = new Label("Assignments");
        categoryTablesLabel.setStyle(
                "-fx-font-weight: bold; " +
                "-fx-font-size: 15pt;");

        categoryTablesLayout.getChildren().add(categoryTablesLabel);
        categoryTablesLayout.setPadding(new Insets(45, 50, 35, 35));

        for (CategoryTable categoryTable : categoryTables) {
            categoryTablesLayout.getChildren().add(categoryTable.categoryTableLayout);
        }

        tableScrollPane.setContent(categoryTablesLayout);
        tableScrollPane.setPannable(true);

        sceneLayout.setLeft(tableScrollPane);

        scrollPane.setContent(sceneLayout);
        scrollPane.setPannable(true);

        scene = new Scene(scrollPane, primaryStage.getWidth(), primaryStage.getHeight());
    }

    /** updates Observable list of Course Category names */
    public void updateCategoryNames() {
        if (dropDownListCategories == null) {
            dropDownListCategories = FXCollections.observableArrayList();
        }
        else {
            dropDownListCategories.clear();

            for (CategoryInterface category : course.getCategories()) {
                dropDownListCategories.add(category.getName());
            }
        }
    }

    public void updateCourseGrade() {
        courseGrade.setText(doubleFormatter.format(categoryCalculator.getCourseGrade(course)));
    }

}
