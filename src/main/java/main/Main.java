package main;

import Model.*;
import GUI.*;
import javafx.application.Application;
import javafx.stage.Stage;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class Main extends Application {

    public static Stage mainStage;
    public static MainGUI gui;
    public static int startMode = 2; //0 = cl, 1 = Course Login, 2 = course scene
    public static Model model;
    public static Main program;
    public static Course testCourse;

    @Override
    public void start(Stage mainApp) throws Exception {
        gui = new MainGUI(); // GUI that contains each scene for our class.
        //mainStage = new Stage();

        if (startMode == 0){
            startCML();
        }else if (startMode == 1){
            mainApp = new Stage();
            mainApp.setTitle("Grade Manager"); // Sets the top bar to "Grade Manager"
            mainApp.setScene(gui.getTitleScene()); // Sets the window the title window, "titleScene"
            mainApp.show(); // Displays the current window.
        }else if (startMode == 2){
            mainApp.setTitle("Grade Manager");
            Course testCourse = buildTestCourse();
            mainApp.setScene((new CourseScene(testCourse)).getScene());
            mainApp.show();
        }
    }

    public static void main(String[] args) {
        launch(args);

        //System.out.println("DEBUG Message in Main.main()");
        //program = new Main();
        //program.launch(args);
        //model = new Model();
        //Scanner console = new Scanner(System.in);
        //CML cml = new CML(console, model, program, args);
    }

    public static void launchGUI(String[] args){
        program.launch(args);
    }

    public static void setCourse(Course c){
        testCourse = c;
    }

    public static void setStartMode(int i){
        startMode = i;
    }

    private void startCML(){
        //Scanner console = new Scanner(System.in);
        //CML cml = new CML(console, model, main, args);
    }



    private Course buildTestCourse(){
        // Quiz
        ArrayList<Assignment> quizAssignments = new ArrayList<Assignment>();
        NumberFormat formatter = new DecimalFormat("#0.00");
        for (int i = 0; i < 8; ++i) {
            Assignment a1 = new Assignment("Quiz " + i, 100, 90 + i);
            quizAssignments.add(a1);
        }
        Category quiz = new Category("Quiz", 0.20, quizAssignments);

        // Test
        ArrayList<Assignment> testAssignments = new ArrayList<Assignment>();
        Assignment b1 = new Assignment("Test 1", 100, 100);
        Assignment b2 = new Assignment("Test 2", 96, 92);
        testAssignments.add(b1);
        testAssignments.add(b2);
        Category test = new Category("Test", 0.40, testAssignments);

        // Project
        ArrayList<Assignment> projectAssignments = new ArrayList<Assignment>();
        Assignment c1 = new Assignment("Project 1", 99, 85);
        projectAssignments.add(c1);
        Category project = new Category("Project", 0.40, projectAssignments);

        ArrayList<Category> categories = new ArrayList<Category>();
        categories.add(quiz);
        categories.add(test);
        categories.add(project);
        return new Course("CS 3398", new School("Texas State"), categories);
    }

}

