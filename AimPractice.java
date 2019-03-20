import java.util.Date;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AimPractice extends Application {
	
	// creates a pane
	private BorderPane pane = new BorderPane();
	
	// creates a circle object with radius of 10
	private static Circle circle = new Circle(10);
	
	// x and y coordinates
	private static int x; private static int y;
	
	// creates a text object
	private Text timerText = new Text();
	
	// new font size to set timerText
	private Font timerFont = new Font(25);
	
	// a button that starts the program
	private Button startButton = new Button("Start");
	
	// start time
	private Date start;
	
	// end time
	private Date end;
	
	// creates a counter so we can check circle clicks
	private static int count = 0;
	
	public void start(Stage primaryStage) throws Exception {
		
		// changes the size of the button so it can be easily clicked
		startButton.setMaxSize(100, 50);
		
		// adds the start button to the pane
		pane.setCenter(startButton);
		
		// when the start button is clicked
		startButton.setOnMouseClicked(e -> {
			
			// gets rid of button to make room for the targets
			pane.getChildren().clear();
			
			// creates a circle with a random color and random (x, y) coordinate
			changeCircle(circle, pane);
			
			// adds the circle to the pane
			pane.getChildren().add(circle);
			// whenever the start button is clicked, start the "timer"
			start = new Date(System.currentTimeMillis());
		});
		
		// when circle is clicked 20 times it clears the pane and prints out the time it took in millis
		circle.setOnMouseClicked(e -> {
			if (count < 19) {
				
				// moves the circle to a random location in the pane with another random color
				changeCircle(circle, pane);
				
				// increases our counter
				count++;
			}
			else {
				
				// clears the last circle to make room for text
				pane.getChildren().clear();
				
				// sets end to the finished time
				end = new Date(System.currentTimeMillis());
		
				// prints out end time - start time
				timerText.setText("Your Time Was " + ((end.getTime() - start.getTime()) / 1000.0) + " Seconds!");
				
				
				timerText.setFont(timerFont);
				// puts the text into the center
				pane.setCenter(timerText);
			}
		});
	
		Scene scene = new Scene(pane, 500, 500);
		primaryStage.setTitle("Hand-Eye Coordination");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	// inserts a circle into a random spot on the pane with a random color 
	public static void changeCircle(Circle circle, BorderPane pane) {
		
		// random rgb ints between 0 - 255 to put into rgb() method
		int red = (int) (Math.random()*256);
		int green = (int) (Math.random()*256);
		int blue = (int) (Math.random()*256);
		
		// sets the random colors to the instanced circle
		// made it dark colors so that the user has an easier time viewing the circle
		circle.setFill(Color.rgb(red, green, blue).darker());
		
		// finds a new random location for the circle for each click
		// high bound is panewidth - circle radius
		// low bound is circle radius
		// 2x the radius to make sure it is in bounds
		x = (int) (Math.random() * ((pane.getWidth() - (2 * circle.getRadius())) + 1) + circle.getRadius());
		y = (int) (Math.random() * ((pane.getHeight() - (2 * circle.getRadius())) + 1) + circle.getRadius());
		circle.setCenterX(x);
		circle.setCenterY(y);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
