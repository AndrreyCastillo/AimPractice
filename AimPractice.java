import java.util.Date;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AimPractice extends Application {
	
	// creates a circle object with radius of 10
	Circle circle = new Circle(10);
	
	// creates a counter so we can check circle clicks
	static int count = 0;
	
	// creates a text object
	Text text = new Text();
	
	public void start(Stage primaryStage) throws Exception {
		
		// adds the circle to the pane
		Pane pane = new Pane(circle);
		
		// tried to keep the circle within bounds
		circle.centerXProperty().bind(pane.widthProperty().subtract(pane.widthProperty().multiply(Math.random()).subtract(circle.getRadius()).add(20)));
		circle.centerYProperty().bind(pane.heightProperty().subtract(pane.heightProperty().multiply(Math.random()).subtract(circle.getRadius()).add(20)));;
		
		// started off with a random color
		circle.setFill(Color.rgb((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256)));
		
		// whenever the program is first ran it creates an object with that start time
		Date start = new Date(System.currentTimeMillis());
		
		// when circle is clicked 20 times it clears the pane and prints out the time it took in millis
		circle.setOnMouseClicked(e -> {
			if (count < 19) {
				
				// clears the pane for each iteration
				pane.getChildren().clear();
				
				// finds a new random location for the circle for each click
				circle.centerXProperty().bind(pane.widthProperty().subtract(pane.widthProperty().multiply(Math.random()).subtract(circle.getRadius()).add(20)));
				circle.centerYProperty().bind(pane.heightProperty().subtract(pane.heightProperty().multiply(Math.random()).subtract(circle.getRadius()).add(20)));;
				
				// gets a new random color for each click
				circle.setFill(Color.rgb((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256)));
				
				// adds a new circle since the previous circle was cleared
				pane.getChildren().add(circle);
				
				// increases our counter
				count++;
			}
			else {
				
				// creates a Date object that has the finished time
				Date end = new Date(System.currentTimeMillis());
				
				// clears the last circle to make room for text
				pane.getChildren().clear();
				
				// prints out end time - start time
				text.setText("Time spent is " + ((end.getTime() - start.getTime()) / 1000.0) + " seconds");
				
				// tried centering the text 
				text.xProperty().bind(pane.widthProperty().divide(4));
				text.yProperty().bind(pane.heightProperty().divide(2));
				
				// adds the text to the pane
				pane.getChildren().add(text);
			}
		});
	
		Scene scene = new Scene(pane, 250, 250);
		primaryStage.setTitle("Hand-Eye Coordination");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
