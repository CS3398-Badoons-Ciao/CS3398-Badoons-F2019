package GUI;

import Interfaces.Listener;
import Interfaces.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CourseScene implements Listener, EventHandler<ActionEvent> {
    // TODO facade to System reference
    Model model;
    Scene scene;

    /**
     * sets model and creates scene
      * @param model
     */
    public CourseScene(Model model) {
        this.model = model;
        initializeScene();

    }

    public Scene getScene() {return scene;};

    /**
     * Uses model to populate scene data
     * @param model the model to update from
     */
    @Override
    public void update(Model model) {

    }

    @Override
    public void register(Model model) {

    }

    void initializeScene() {
        VBox vBox = new VBox(new Label("Courses"));
        Button saveBtn = new Button("Save");
        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //  TODO call facade method to handle
            }
        });
        Button exitBtn = new Button("Exit");
        exitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //  TODO call facade method to handle
            }
        });
        vBox.getChildren().addAll(saveBtn, exitBtn);
        scene = new Scene(vBox, 400, 400);
    }


    @Override
    public void handle(ActionEvent event) {

    }
}
