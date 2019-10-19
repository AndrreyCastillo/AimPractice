package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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

    // text to display timer later
    private Text timerText = new Text();
    private Font timerFont = new Font(25);

    // end time
    private Date end;

    // a counter for whenever the circle is missed
    private static int misclicks = 0;

    @Override
    public void start(Stage primaryStage) {
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
        startButton.setOnMouseClicked(e -> Controller.startAction());

        // when the enter key is pressed on the start button
        startButton.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                Controller.startAction();
            }
        });

        //when circle is clicked 20 times it clears the pane and prints out the time taken
        circle.setOnMouseClicked(e -> {
            if (count < 19) {

                // moves the circle to a random location in the pane with another random color
                Controller.changeCircle(circle, mainPane);

                // increases counter and display it in the corner
                mainPane.setTop(new Text(" " + (++count)));
            } else {

                // subtracts misclicks and count because I think the circles when clicked are counted as the pane
                misclicks -= count;

                // clears the last circle to make room for text
                mainPane.getChildren().clear();

                // sets end to the finished time
                end = new Date(System.currentTimeMillis());

                // prints out end time - start time
                float time = (end.getTime() - start.getTime()) / 1000.0f;
                timerText.setText("Time: " + time + " Seconds\n" + "Misclicks: " + misclicks);
                timerText.setFont(timerFont);

                // puts the text into the center
                mainPane.setCenter(timerText);

                // Top Ten
                File highScoreFile = new File("highscores.txt");
                try {
                    highScoreFile.createNewFile();
                    BufferedReader br = new BufferedReader(new FileReader(highScoreFile));
                    ArrayList<Float> highScores = new ArrayList<>();

                    // Read all stored records, and calculate their scores (given the currently defined score function)
                    while (br.ready()) {
                        String[] record = br.readLine().split(" ");
                        float record_time = Float.valueOf(record[0]);
                        int record_misclicks = Integer.valueOf(record[1]);
                        highScores.add(score(record_time, record_misclicks));
                    }

                    // Add to these scores the current score and sort from high to low.
                    float currentScore = score(time, misclicks);
                    highScores.add(currentScore);
                    highScores.sort(Comparator.reverseOrder());
                    List<Float> topTen = highScores.subList(0, highScores.size() >= 10 ? 10 : highScores.size()); // Useable Top Ten.

                    // For Debugging.
                    System.out.println("Current Score: " + currentScore);
                    System.out.println(Arrays.toString(topTen.toArray()));

                    // Write current record back to file.
                    FileWriter fw = new FileWriter(highScoreFile, true);
                    fw.append(time + " " + misclicks + "\n");

                    br.close();
                    fw.close();
                } catch (IOException e1) {
                    // Could not read/write file.
                    e1.printStackTrace();
                }
            }
        });

        circlePane.setOnMouseClicked(e -> misclicks++);

        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hand-Eye Coordination");
        Scene scene = new Scene(mainPane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Score function.
     */
    private static float score(float time, int misclicks) {
        return (count - misclicks) / time;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
