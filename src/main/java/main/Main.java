package main;

import Model.*;
import GUI.*;

import javafx.application.Application;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;



public class Main extends Application {

    public Main() {

    }

    public static Stage mainStage;
    public static MainGUI gui;
    public static int startMode = 2 ; //0 = cml, 1 = charlies, 2 = Jons
    public static Model model;
    public static Main program;

    public static Course testCourse;

    @Override
    public void start(Stage mainApp) throws Exception {
        gui = new MainGUI(); // GUI that contains each scene for our class.
        mainStage = new Stage();

        if (startMode == 0){
            startCML();
        }else if (startMode == 1){
            mainApp = mainStage;
            mainApp.setTitle("Grade Manager"); // Sets the top bar to "Grade Manager"
            mainApp.setScene(gui.getTitleScene()); // Sets the window the title window, "titleScene"
            mainApp.show(); // Displays the current window.
        }else if (startMode == 2){
            mainApp.setTitle("Grade Manager");
            mainApp.setWidth(400);
            mainApp.setHeight(500);
            //Course testCourse = buildTestCourse();
            mainApp.setScene((new CourseScene(testCourse)).getScene());
            mainApp.show();
        }
    }



    public static void main(String[] args) {
        //System.out.println("DEBUG Message in Main.main()");
        program = new Main();

        //launch(args);
        //program.launch(args);
        model = new Model();
        Scanner console = new Scanner(System.in);
        CML cml = new CML(console, model, program, args);




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

    public Course buildTestCourse(){
        // Quiz
        ArrayList<Assignment> quizAssignments = new ArrayList<Assignment>();
        NumberFormat formatter = new DecimalFormat("#0.00");
        for (int i = 0; i < 8; ++i) {
            Assignment a1 = new Assignment("Quiz " + i, 100, 90 + i);
            quizAssignments.add(a1);
        }
        Category quiz = new Category("Quiz", 0.25, quizAssignments);

        // Test
        ArrayList<Assignment> testAssignments = new ArrayList<Assignment>();
        Assignment b1 = new Assignment("Test 1", 100, 100);
        Assignment b2 = new Assignment("Test 2", 96, 92);
        testAssignments.add(b1);
        testAssignments.add(b2);
        Category test = new Category("Test", 0.75, testAssignments);

        ArrayList<Category> categories = new ArrayList<Category>();
        categories.add(quiz);
        categories.add(test);
        return new Course("CS3398", new School("Texas State"), categories);
    }

}

