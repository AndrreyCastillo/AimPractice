package sample;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.Date;

public class Controller {

    /* moves the same circle to a different location and also fills it with a random color */
    static void changeCircle(Circle circle, BorderPane pane) {

        // random rgb ints between 0 - 255 to put into rgb() method
        int red = (int) (Math.random()*256);
        int green = (int) (Math.random()*256);
        int blue = (int) (Math.random()*256);

        // sets the random colors to the instanced circle
        // made it dark colors so that the user has an easier time viewing the circle
        circle.setFill(Color.rgb(red, green, blue).darker());

        // finds a new random location for the circle for each click
        // high bound is pane width - circle radius
        // low bound is circle radius
        // 2x the radius to make sure it is in bounds
        // (int) (Math.random() * ((upper - lower) + 1) + lower)
        // x and y coordinates for the circle so we can change it later
        int x = (int) (Math.random() * (((pane.getWidth() - (circle.getRadius())) - (circle.getRadius())) + 1) + (circle.getRadius()));
        int y = (int) (Math.random() * (((pane.getHeight() - (2 * circle.getRadius())) - (2 * circle.getRadius())) + 1) + (2 * circle.getRadius()));
        circle.setCenterX(x);
        circle.setCenterY(y);
    }

    /* When an action is performed on the start button */
    static void startAction() {

        // gets rid of button to make room for the targets and counter text in the corner
        Main.mainPane.getChildren().clear();
        Main.mainPane.setTop(new Text(" " + Main.count));
        Main.mainPane.setCenter(Main.circlePane);

        Main.count = 0;
        Main.misclicks = 0;

        // creates a circle with a random color and random (x, y) coordinate
        changeCircle(Main.circle, Main.mainPane);
        Main.circlePane.getChildren().add(Main.circle);

        // whenever the start button is clicked, start the "timer"
        Main.start = new Date(System.currentTimeMillis());
    }
}
