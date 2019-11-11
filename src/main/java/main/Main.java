package main;

import GUI.*;
import Model.Model;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Grade Manager");
        Model model = new Model();
        primaryStage.setScene((new LogIn(model, primaryStage)).getScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
            launch(args);
    }

}

