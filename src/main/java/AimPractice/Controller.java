package AimPractice;

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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class Controller {

    /*
     * FXML Variables
     */
    @FXML
    private Label clicksTracker;
    @FXML
    private Text title;
    @FXML
    private VBox homeBox;
    @FXML
    private BorderPane mainPane;
    @FXML
    private Button startButton;
    @FXML
    private Pane circlePane;
    @FXML
    private Label timerText;
    @FXML
    private Circle circle;

    private int count = 0;
    private int missclicks = 0;
    private int totalClicks = 20;
    private Date start;
    private String saveFileName = "highscores.txt";
    private int topSize = 10;

    /* moves the same circle to a different location and also fills it with a random color */
    private void changeCircle(Circle circle, BorderPane pane) {

        // random rgb ints between 0 - 255 to put into rgb() method
        int red = (int) (Math.random() * 256);
        int green = (int) (Math.random() * 256);
        int blue = (int) (Math.random() * 256);

        // sets the random colors to the instanced circle
        // made it dark colors so that the user has an easier time viewing the circle
        circle.setFill(Color.rgb(red, green, blue).darker());

        // finds a new random location for the circle for each click
        // high bound is pane width - circle radius
        // low bound is circle radius
        // 2x the radius to make sure it is in bounds
        // (int) (Math.random() * ((upper - lower) + 1) + lower)
        // x and y coordinates for the circle so we can change it later
        int x = (int) (Math.random() * (((pane.getWidth() - (circle.getRadius())) - (circle.getRadius())) + 1)
                + (circle.getRadius()));
        int y = (int) (Math.random() * (((pane.getHeight() - (2 * circle.getRadius())) - (2 * circle.getRadius())) + 1)
                + (2 * circle.getRadius()));
        circle.setCenterX(x);
        circle.setCenterY(y);
    }

    /* When an action is performed on the start button */
    private void startAction() {

        //Set homeBox to "not visible"
        homeBox.setVisible(false);

        //Visible FXML Elements considering circles
        circlePane.setVisible(true);
        circle.setVisible(true);

        //Initialize the clicks tracker
        upgradeClicksTracker();

        //randomize first circle
        changeCircle(circle, mainPane);

        // whenever the start button is clicked, start the "timer"
        start = new Date(System.currentTimeMillis());
    }

    private void upgradeClicksTracker() {
        clicksTracker.setText(count + "/" + totalClicks);
    }

    @FXML
    private void startButtonPressed(ActionEvent actionEvent) {
        startAction();
    }

    @FXML
    public void mainPaneKeyPressed(KeyEvent keyEvent) {
        //Enter Pressed (Set on mainPane in case of startButton loosing focus)
        if (keyEvent.getCode() == KeyCode.ENTER) {
            startAction();
        }
    }

    @FXML
    public void circleMouseClicked(MouseEvent mouseEvent) {
        if (count < totalClicks - 1) {
            //Increment count and set text to count
            ++count;
            upgradeClicksTracker();

            //randomize circle
            changeCircle(circle, mainPane);
        } else {
            //Set not needed elements to not visible
            circlePane.setVisible(false);
            clicksTracker.setVisible(false);

            // subtracts misclicks and count because I think the circles when clicked are counted as the pane
            missclicks -= count;

            // sets end to the finished time
            Date end = new Date(System.currentTimeMillis());
            float time = (end.getTime() - start.getTime()) / 1000.f;
            float score = score(time, missclicks);

            // prints out end time - start time
            timerText.setText("Time: " + time + " Seconds\n" + "Misclicks: " + missclicks + "\nScore: " + score);

            //Show result
            timerText.setVisible(true);

            // Save time & missclicks
            saveRecord(time, missclicks);
            // Returns top scores
            Float[] top = getTop();
            // Print top
            System.out.println("Current Score: " + score);
            System.out.println("Top scores:\n" + Arrays.toString(top));
        }
    }

    private float score(float time, int missclicks) {
        return (count - missclicks) / time; // Can easily be changed.
    }

    private void saveRecord(float time, int misclicks) {
        File highScoreFile = new File(saveFileName);
        try {
            FileWriter fw = new FileWriter(highScoreFile, true);
            fw.append(time + " " + misclicks + "\n");
            fw.close();
        } catch (IOException e1) {
            // Could not read/write file.
            e1.printStackTrace();
        }
    }

    private Float[] getTop() {
        File highScoreFile = new File(saveFileName);
        Float[] top = new Float[topSize];
        try {
            highScoreFile.createNewFile();
            BufferedReader br = new BufferedReader(new FileReader(highScoreFile));

            // Read all stored records, and calculate their scores (given the currently defined score function)
            ArrayList<Float> highScores = new ArrayList<>();
            while (br.ready()) {
                String[] record = br.readLine().split(" ");
                float record_time = Float.valueOf(record[0]);
                int record_misclicks = Integer.valueOf(record[1]);
                highScores.add(score(record_time, record_misclicks));
            }
            br.close();

            // Useable Top.
            highScores.sort(Comparator.reverseOrder());
            List<Float> topList = highScores.subList(0, highScores.size() >= topSize ? topSize : highScores.size());

            // Put list into top array.
            topList.toArray(top);
        } catch (IOException e1) {
            // Could not read/write file.
            e1.printStackTrace();
        }
        // Return top array.
        return top;
    }

    @FXML
    public void circlePaneMouseClicked(MouseEvent mouseEvent) {
        //Increment missclicks
        ++missclicks;
    }
}
