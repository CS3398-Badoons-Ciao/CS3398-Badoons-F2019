

import Model.*;
import GUI.*;

import javafx.application.Application;
import javafx.stage.Stage;
import java.util.Scanner;



public class Main extends Application {

    static Stage mainApp;
    static MainGUI gui;
    int startMode = 0 ; //0 = cml, 1 = charlies, 2 = Jons
    Model model;

    @Override
    public void start(Stage main) throws Exception {
        model = new Model();
        if (startMode == 0){
            startCML();
        }else if (startMode == 1){
            gui = new MainGUI(); // GUI that contains each scene for our class.
            mainApp = new Stage();
            main = mainApp;
            mainApp.setTitle("Grade Manager"); // Sets the top bar to "Grade Manager"
            mainApp.setScene(gui.getTitleScene()); // Sets the window the title window, "titleScene"
            mainApp.show(); // Displays the current window.
        }else if (startMode == 2){
            //primaryStage.setTitle("Grade Manager");


           //primaryStage.setScene((new CourseScene(new ConcreteModel())).getScene());
           //primaryStage.show();
        }
    }



    public static void main(String[] args) {
        System.out.println("DEBUG Message in Main.main()");
        launch(args);
    }

    public void startCML(){
        try{
            Scanner console = new Scanner(System.in);
            CML cml = new CML(console, model);
        }catch(Exception e){
            System.out.println(e);
        }

    }

    // This method allows the FXML controllers to set the main application scene.
    public static void displayTitle() {
        mainApp.setScene(gui.getTitleScene());
    }
    // This method allows the FXML controllers to set the main application scene.
    public static void displayCourseOverview() {
        mainApp.setScene(gui.getCourseOverviewScene());
    }
}
