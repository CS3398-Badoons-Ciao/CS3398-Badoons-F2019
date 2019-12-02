package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Contains header and footer Hbox Layouts
 */
public class BoxSplitLayout extends VBox {
    /**
     * Label Node in headLayout
     */
    public Label headLabel = new Label();

    /**
     * child Layout for head
     */
    public HBox headLayout = new HBox();
    /**
     * a child layout body
     */
    public VBox bodyLayout = new VBox();

    /**
     * sets basic style for head and body
     */
    public BoxSplitLayout() {
        headLabel.setStyle( "-fx-font-weight: bold;" +
                            "-fx-font-size: 14");
        headLabel.setAlignment(Pos.CENTER);

        headLayout.getChildren().add(headLabel);
        headLayout.setAlignment(Pos.CENTER);
        headLayout.setPadding(new Insets(10,10,10,10));
        headLayout.setBackground(SceneStyle.getSecondaryBackground());

        bodyLayout.setSpacing(7);
        bodyLayout.setPadding(new Insets(10,10,10,10));
        headLayout.setBackground(SceneStyle.getSecondaryBackground());

        getChildren().addAll(headLayout, bodyLayout);
        setStyle("-fx-border-color: black");
    }

    public Label getHeadLabel() {
        return headLabel;
    }
}
