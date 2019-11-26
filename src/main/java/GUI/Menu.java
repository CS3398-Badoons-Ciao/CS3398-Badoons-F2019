package GUI;

import Interfaces.CategoryCalculatorInterface;
import Interfaces.Listener;
import Interfaces.Publisher;
import Model.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Menu implements Listener, EventHandler<ActionEvent>
{
    private Scene menuScene;/** The primary Stage */
    private Stage primaryStage;

    /** Course Scene for Stage */
    private Scene scene;

    /** User Model */
    private Model model;

    /** 'scroll bar' feature */
    private ScrollPane scrollPane = new ScrollPane();

    /** root layout */
    private VBox sceneLayout = new VBox();

    /** 'Menu Bar' feature */
    private MenuBar menuBar = new MenuBar();

    /** root layout for scene title */
    private HBox titleLayout = new HBox();

    /** root layout for 'menu options' feature */
    private BoxSplitLayout menuOptionsLayout = new BoxSplitLayout();

    public Menu(Stage primaryStage, Model model)
    {
        this.primaryStage = primaryStage;
        this.model = model;
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

    @Override
    public void handle(ActionEvent event) { }

    private void buildScene()
    {
        //build all of the aspects of the Scene
        buildMenuBar();
        buildMenuOptionsLayout();

        //Set up some other parameters
        //feel free to tweak these as you see fit
        sceneLayout.setFillWidth(true);
        sceneLayout.setSpacing(20);
        sceneLayout.setPadding(new Insets(5, 35, 35, 35));

        sceneLayout.getChildren().addAll(menuBar, titleLayout, menuOptionsLayout);

        scrollPane.setContent(sceneLayout);
        scrollPane.setPannable(true);
        scrollPane.fitToWidthProperty().set(true);
        scrollPane.fitToHeightProperty().set(true);

        scene = new Scene(scrollPane,900,600);

    }

    //builds the menu bar at the top of the screen
    private void buildMenuBar() {
        javafx.scene.control.Menu fileMenu = new javafx.scene.control.Menu("File");

        MenuItem logOutItem = new MenuItem("Log Out");
        logOutItem.setOnAction(event -> {
            try {
                primaryStage.setScene((new LogIn(model, primaryStage)).getScene());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        fileMenu.getItems().addAll(logOutItem);

        menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu);
    }

    //build the button that directs to the courses scene
    private void buildMenuOptionsLayout()
    {
        menuOptionsLayout.headLabel.setText("Menu Options");

        //The 3 menu option buttons
        //these buttons allow the user to navigate to the corresponding scene
        final Button goToCoursesSceneBtn = new Button("Courses");
        final Button goToToDoListSceneBtn = new Button("To Do List");
        final Button goToOptionsSceneBtn = new Button("Options");

        //tooltips that display when hovering over the corresponding buttons
        final Tooltip coursesTooltip = new Tooltip("Goes to the course overview scene");
        final Tooltip toDoListTooltip = new Tooltip("Goes to the To-Do list scene");
        final Tooltip optionsTooltip = new Tooltip("Goes to the Options scene");

        //install method assigns the tooltip with the button
        Tooltip.install(goToCoursesSceneBtn, coursesTooltip);
        Tooltip.install(goToToDoListSceneBtn, toDoListTooltip);
        Tooltip.install(goToOptionsSceneBtn, optionsTooltip);

        goToCoursesSceneBtn.setOnAction(event ->
        {
            primaryStage.setScene((new CourseOverview(model, primaryStage)).getScene());
        });

        goToToDoListSceneBtn.setOnAction(event ->
        {
            //TODO add the ToDoListScene class, and uncomment the following line of code
            //primaryStage.setScene((new ToDoListScene(model, primaryStage)).getScene());
        });

        goToOptionsSceneBtn.setOnAction(event ->
        {
            //TODO add the OptionsScene class, and uncomment the following line of code
            //primaryStage.setScene((new OptionsScene(model, primaryStage)).getScene());
        });

        menuOptionsLayout.bodyLayout.getChildren().addAll(
                goToCoursesSceneBtn, goToToDoListSceneBtn, goToOptionsSceneBtn);
    }
}
