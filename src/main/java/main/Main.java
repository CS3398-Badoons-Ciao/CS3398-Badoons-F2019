package main;

import GUI.*;
import javafx.application.Application;
import javafx.stage.Stage;

// comment for test branch

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scenes mainGUI = new Scenes(primaryStage);
        primaryStage.setTitle("Grade Manager");
        primaryStage.setScene(mainGUI.getTitleScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
            launch(args);
    }

}

