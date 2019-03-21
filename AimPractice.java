import java.util.Date;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AimPractice extends Application {
	
	private static BorderPane mainPane = new BorderPane();
	
	// this pane is to put all the circles on
	private static Pane circlePane = new Pane();
	
	// creates a circle object with radius of 10
	private static Circle circle = new Circle(10);
	
	// x and y coordinates for the circle so we can change it later
	private static int x; private static int y;
	
	// Start Screen Stuff
	private Text welcomeText = new Text("Practice Your Aim!");
	private Font welcomeFont = new Font(40);
	private VBox welcomeTextArea = new VBox(welcomeText);
	private Button startButton = new Button("Start");

	// text to display timer later
	private Text timerText = new Text();
	private Font timerFont = new Font(25);
	
	// start time
	private static Date start;
	
	// end time
	private Date end;
	
	// a counter so we can check circle clicks
	private static int count = 0;
	
	// a counter for whenever the circle is missed
	private static int misclicks = 0;
	
	public void start(Stage primaryStage) throws Exception {
		
		// changes the size of the button so it can be easily clicked
		startButton.setMaxSize(100, 50);
		
		// increases the size of the text to 50
		welcomeText.setFont(welcomeFont);
		welcomeTextArea.setPadding(new Insets(50, 0, 0, 0));
		welcomeTextArea.setAlignment(Pos.CENTER);
		mainPane.setTop(welcomeTextArea);
		
		// adds the start button to the pane
		mainPane.setCenter(startButton);
		
		// when the start button is clicked
		startButton.setOnMouseClicked(e -> {
			startAction();
		});
		
		// when the enter key is pressed on the start button
		startButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				startAction();
			}
		});
		
		// when circle is clicked 20 times it clears the pane and prints out the time taken
		circle.setOnMouseClicked(e -> {
			if (count < 19) {
				
				// moves the circle to a random location in the pane with another random color
				changeCircle(circle, mainPane);
				
				// increases counter and display it in the corner
				mainPane.setTop(new Text(" " + (++count)));
			}
			else {
				
				// subtracts misclicks and count because I think the circles when clicked are counted as the pane
				misclicks -= count;
				
				// clears the last circle to make room for text
				mainPane.getChildren().clear();
				
				// sets end to the finished time
				end = new Date(System.currentTimeMillis());
		
				// prints out end time - start time
				timerText.setText("Time: " + ((end.getTime() - start.getTime()) / 1000.0) + " Seconds\n" + 
									"Misclicks: " + misclicks);
				timerText.setFont(timerFont);
				
				// puts the text into the center
				mainPane.setCenter(timerText);
			}
		});
		
		circlePane.setOnMouseClicked(e -> {
			misclicks++;
		});
	
		Scene scene = new Scene(mainPane, 500, 500);
		primaryStage.setTitle("Hand-Eye Coordination");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/* moves the same circle to a different location and also fills it with a random color */
	private static void changeCircle(Circle circle, BorderPane pane) {
		
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
		// (int) (Math.random() * ((upper - lower) + 1) + lower)
		x = (int) (Math.random() * (((pane.getWidth() - (circle.getRadius())) - (circle.getRadius())) + 1) + (circle.getRadius()));
		y = (int) (Math.random() * (((pane.getHeight() - (2 * circle.getRadius())) - (2 * circle.getRadius())) + 1) + (2 * circle.getRadius()));
		circle.setCenterX(x);
		circle.setCenterY(y);
	}
	
	/* When an action is performed on the start button */
	private static void startAction() {
		
		// gets rid of button to make room for the targets and counter text in the corner
		mainPane.getChildren().clear();
		mainPane.setTop(new Text(" " + count));
		mainPane.setCenter(circlePane);
		
		// creates a circle with a random color and random (x, y) coordinate
		changeCircle(circle, mainPane);
		circlePane.getChildren().add(circle);
		
		// whenever the start button is clicked, start the "timer"
		start = new Date(System.currentTimeMillis());
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
