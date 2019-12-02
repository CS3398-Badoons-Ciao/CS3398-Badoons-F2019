package GUI;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class SceneStyle {
    private static BackgroundFill backgroundFill = new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY);
    private static Background background = new Background((backgroundFill));

    private static BackgroundFill secondaryBackgroundFill = new BackgroundFill(Color.KHAKI, CornerRadii.EMPTY, Insets.EMPTY);
    private static Background secondaryBackground = new Background((secondaryBackgroundFill));

    public static void setBackgroundColor(Color color) {
        backgroundFill = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background((backgroundFill));
    }

    public static void setSecondaryBackgroundColor(Color color) {
        secondaryBackgroundFill = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
        Background secondaryBackground = new Background((backgroundFill));
    }

    public static Background buildBackground(Color color) {
        BackgroundFill fill = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
        return new Background((backgroundFill));
    }

    public static Background getBackground() {
        return background;
    }
    public static Background getSecondaryBackground() {return secondaryBackground;}
}