package GUI;

import Interfaces.CategoryCalculatorInterface;
import Interfaces.Listener;
import Interfaces.Publisher;
import Model.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Menu
{
    private Scene menuScene;

    /** The primary Stage */
    private Stage primaryStage;

    /** Course Scene for Stage */
    private Scene scene;

    /** User Model */
    private Model model;

    /** root layout */
    private VBox sceneLayout = new VBox();

    /** 'Menu Bar' feature */
    private MenuBar menuBar = new MenuBar();

    /** root layout for 'menu options' feature */
    private VBox menuOptionsLayout = new VBox();

    public Menu(Stage primaryStage, Model model)
    {
        this.primaryStage = primaryStage;
        this.model = model;
        buildScene();
    }

    public Scene getScene() {return scene;}

    private void buildScene()
    {

        buildMenuBar();
        buildMenuOptionsLayout();

        sceneLayout.setBackground(SceneStyle.getBackground());
        sceneLayout.setAlignment(Pos.BASELINE_CENTER);
        sceneLayout.getChildren().add(menuBar);
        sceneLayout.getChildren().add(menuOptionsLayout);

        scene = new Scene(sceneLayout, primaryStage.getWidth(), primaryStage.getHeight());
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
        menuOptionsLayout.setAlignment(Pos.CENTER);
        menuOptionsLayout.setFillWidth(false);
        menuOptionsLayout.setPadding(new Insets(30,0,30,0));
        menuOptionsLayout.setSpacing(10);
        menuOptionsLayout.setBackground(SceneStyle.getSecondaryBackground());

        //The 3 menu option buttons
        //these buttons allow the user to navigate to the corresponding scene
        final Button goToCoursesSceneBtn = new Button("Courses");
        final Button goToToDoListSceneBtn = new Button("To Do List");
        final Button goToOptionsSceneBtn = new Button("Options");

        goToCoursesSceneBtn.setPrefSize(115,25);
        goToToDoListSceneBtn.setPrefSize(115,25);
        goToOptionsSceneBtn.setPrefSize(115,25);

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

        final Label menuLabel = new Label("Menu");
        menuLabel.setStyle(
                "-fx-font-size: 40pt;");
        menuLabel.setPadding(new Insets(0, 10,50,0));

        menuOptionsLayout.getChildren().addAll(
                menuLabel, goToCoursesSceneBtn, goToToDoListSceneBtn, goToOptionsSceneBtn);
    }
}
