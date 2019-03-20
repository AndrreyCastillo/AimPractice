import java.util.Date;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AimPractice extends Application {
	
	// creates a pane
	private BorderPane pane = new BorderPane();
	
	// creates a circle object with radius of 10
	private Circle circle = new Circle(10);
	
	// creates a text object
	private Text timerText = new Text();
	
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
			
			// adds a random circle
			insertCircle(circle, pane);
			
			// whenever the program is first ran it creates an object with that start time
			start = new Date(System.currentTimeMillis());
		});
		
		
		
		// when circle is clicked 20 times it clears the pane and prints out the time it took in millis
		circle.setOnMouseClicked(e -> {
			if (count < 19) {
				
				// clears the pane for each iteration
				pane.getChildren().clear();
				
				// adds a random circle
				insertCircle(circle, pane);
				
				// increases our counter
				count++;
			}
			else {
				
				// creates a Date object that has the finished time
				end = new Date(System.currentTimeMillis());
				
				// clears the last circle to make room for text
				pane.getChildren().clear();
				
				// prints out end time - start time
				timerText.setText("Time spent is " + ((end.getTime() - start.getTime()) / 1000.0) + " seconds");
				
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
	public static void insertCircle(Circle circle, Pane pane) {
		
		// finds a new random location for the circle for each click
		circle.centerXProperty().bind(pane.widthProperty().subtract(pane.widthProperty().multiply(Math.random()).subtract(circle.getRadius()).add(20)));
		circle.centerYProperty().bind(pane.heightProperty().subtract(pane.heightProperty().multiply(Math.random()).subtract(circle.getRadius()).add(20)));;
		
		// gets a new random color for each click
		circle.setFill(Color.rgb((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256)));
		
		// adds a new circle since the previous circle was cleared
		pane.getChildren().add(circle);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
