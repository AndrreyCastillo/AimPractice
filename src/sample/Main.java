package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Date;

public class Main extends Application {

    static BorderPane mainPane = new BorderPane();

    // this pane is to put all the circles on
    static Pane circlePane = new Pane();

    // creates a circle object with radius of 10
    static Circle circle = new Circle(10);

    // start time
    static Date start;

    // a counter so we can check circle clicks
    static int count = 0;

    // Start Screen Stuff
    private Text welcomeText = new Text("Practice Your Aim!");
    private Font welcomeFont = new Font(40);
    private VBox welcomeTextArea = new VBox(welcomeText);
    private Button startButton = new Button("Start");
    private VBox startButtonContainer = new VBox(startButton);

    // text to display timer later
    private Text timerText = new Text();
    private Font timerFont = new Font(25);
    private VBox timerContainer = new VBox(timerText);

    // End Screen Stuff
    private Button homeButton = new Button("Home");
    private Button restartButton = new Button("Restart");
    private HBox endScreenButtons = new HBox(homeButton, restartButton);

    // end time
    private Date end;

    // a counter for whenever the circle is missed
    static int misclicks = 0;

    @Override
    public void start(Stage primaryStage) {
        // changes the size of the button so it can be easily clicked
        startButton.setPrefSize(100, 50);
        homeButton.setPrefSize(100, 50);
        restartButton.setPrefSize(100, 50);

        // increases the size of the text to 50
        welcomeText.setFont(welcomeFont);

        // Paddings
        welcomeTextArea.setPadding(new Insets(150, 0, 0, 0));
        welcomeTextArea.setAlignment(Pos.CENTER);

        endScreenButtons.setSpacing(25);
        endScreenButtons.setPadding(new Insets(0, 0, 150, 0));
        endScreenButtons.setAlignment(Pos.CENTER);

        timerContainer.setPadding(new Insets(150, 0, 0, 0));
        timerContainer.setAlignment(Pos.CENTER);

        startButtonContainer.setPadding(new Insets(0, 0, 150, 0));
        startButtonContainer.setAlignment(Pos.CENTER);

        showHome();

        // when the start button is clicked
        startButton.setOnMouseClicked(e -> Controller.startAction());

        // when the enter key is pressed on the start button
        startButton.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                Controller.startAction();
            }
        });

        // when the restart button is clicked
        restartButton.setOnMouseClicked(e -> Controller.startAction());

        // when the enter key is pressed on the restart button
        restartButton.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                Controller.startAction();
            }
        });

        // when the end button is clicked
        homeButton.setOnMouseClicked(e -> showHome());

        // when the enter key is pressed on the end button
        homeButton.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                showHome();
            }
        });

        //when circle is clicked 20 times it clears the pane and prints out the time taken
        circle.setOnMouseClicked(e -> {
            if (count < 19) {

                // moves the circle to a random location in the pane with another random color
                Controller.changeCircle(circle, mainPane);

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
                mainPane.setTop(timerContainer);

                mainPane.setBottom(endScreenButtons);
            }
        });

        circlePane.setOnMouseClicked(e -> misclicks++);

        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Aim Practice");
        Scene scene = new Scene(mainPane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showHome() {
        // clears the last circle to make room for text
        mainPane.getChildren().clear();

        // Set welcome text
        mainPane.setTop(welcomeTextArea);

        // adds the start button to the pane
        mainPane.setBottom(startButtonContainer);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
