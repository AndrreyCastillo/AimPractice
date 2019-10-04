package sample;

import java.util.Timer;
import java.util.TimerTask;

import static sample.Main.circle;
import static sample.Main.mainPane;

public class DifficultyTimer {

    static Timer timer;
    static int time;

    static void initialize() {
        if (timer != null) {
            timer.cancel();
        }
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                update();
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, time);
    }

    static void update() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Controller.changeCircle(circle, mainPane);
            }
        };
        timer.cancel();
        timer = new Timer();
        timer.schedule(timerTask, time);

    }

    static void setDifficulty(String difficulty) {
        switch(difficulty) {
            case "Easy":
                time = 1100;
                break;
            case "Normal":
                time = 850;
                break;
            case "Hard":
                time = 450;
                break;
        }
    }

}
