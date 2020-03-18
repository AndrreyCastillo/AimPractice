package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.Date;

public class Controller {

    /*
     * FXML Variables
     */
	@FXML
    private Text clicksTracker;
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
    private Text timerText;
    @FXML
    private Circle circle;

    private int count = 0;
    private int missclicks = 0;
    private int totalClicks = 20;
    private Date start;


    /* moves the same circle to a different location and also fills it with a random color */
    private void changeCircle(Circle circle, BorderPane pane) {

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

    private void upgradeClicksTracker()
    {
    	clicksTracker.setText(count + "/" + totalClicks);
    }

    @FXML
	private void startButtonPressed(ActionEvent actionEvent)
	{
	    startAction();
	}

	@FXML
    public void mainPaneKeyPressed(KeyEvent keyEvent)
    {
        //Enter Pressed (Set on mainPane in case of startButton loosing focus)
        if(keyEvent.getCode() == KeyCode.ENTER)
        {
            startAction();
        }
    }

    @FXML
    public void circleMouseClicked(MouseEvent mouseEvent)
    {
        if(count < totalClicks - 1)
        {
            //Increment count and set text to count
            ++count;
            upgradeClicksTracker();

            //randomize circle
            changeCircle(circle, mainPane);
        }
        else
        {
            //Set not needed elements to not visible
            circlePane.setVisible(false);
            clicksTracker.setVisible(false);

            // subtracts misclicks and count because I think the circles when clicked are counted as the pane
            missclicks -= count;

            // sets end to the finished time
            Date end = new Date(System.currentTimeMillis());

            // prints out end time - start time
            timerText.setText("Time: " + ((end.getTime() - start.getTime()) / 1000.0) + " Seconds\n" +
                    "Misclicks: " + missclicks);

            //Show result
            timerText.setVisible(true);
        }
    }

    @FXML
    public void circlePaneMouseClicked(MouseEvent mouseEvent)
    {
        //Increment missclicks
        ++missclicks;
    }
}
