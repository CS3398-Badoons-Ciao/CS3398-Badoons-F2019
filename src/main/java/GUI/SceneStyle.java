package GUI;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * Stores Scene Backgrounds
 */
public class SceneStyle {
    private static Color defaultPrimaryColor = Color.web("0xCCE6FF");
    private static Color defaultSecondaryColor = Color.web("0x99B3FF");

    private static Color primaryBackgroundColor = defaultPrimaryColor;
    private static BackgroundFill primaryBackgroundFill = new BackgroundFill(primaryBackgroundColor, CornerRadii.EMPTY, Insets.EMPTY);
    private static Background primaryBackground = new Background((primaryBackgroundFill));

    private static Color secondaryBackgroundColor = defaultSecondaryColor;
    private static BackgroundFill secondaryBackgroundFill = new BackgroundFill(secondaryBackgroundColor, CornerRadii.EMPTY, Insets.EMPTY);
    private static Background secondaryBackground = new Background((secondaryBackgroundFill));

    public static void setPrimaryBackgroundColor(Color color) {
        primaryBackgroundColor = color;
        primaryBackgroundFill = new BackgroundFill(primaryBackgroundColor, CornerRadii.EMPTY, Insets.EMPTY);
        primaryBackground = new Background((primaryBackgroundFill));
    }

    public static void setSecondaryBackgroundColor(Color color) {
        secondaryBackgroundColor = color;
        secondaryBackgroundFill = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
        secondaryBackground = new Background((secondaryBackgroundFill));
    }

    public static Background buildBackground(Color color) {
        BackgroundFill fill = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
        return new Background((primaryBackgroundFill));
    }

    public static void setDefaultColor() {
        primaryBackgroundColor = Color.BEIGE;
        primaryBackgroundFill = new BackgroundFill(primaryBackgroundColor, CornerRadii.EMPTY, Insets.EMPTY);
        primaryBackground = new Background((primaryBackgroundFill));

        secondaryBackgroundColor = Color.KHAKI;
        secondaryBackgroundFill = new BackgroundFill(secondaryBackgroundColor, CornerRadii.EMPTY, Insets.EMPTY);
        secondaryBackground = new Background((secondaryBackgroundFill));
    }

    public static Color getPrimaryBackgroundColor() {return primaryBackgroundColor;}
    public static Color getSecondaryBackgroundColor() {return secondaryBackgroundColor;}
    public static Background getPrimaryBackground() {
        return primaryBackground;
    }
    public static Background getSecondaryBackground() {return secondaryBackground;}
}