package testgroup.plantsvszombies.zombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import testgroup.plantsvszombies.Grid;
import testgroup.plantsvszombies.PlayGame;
import testgroup.plantsvszombies.Playable;

import java.io.Serializable;
import java.util.Random;

public class ZombieGenerator implements Serializable {
    protected Grid grid;
    protected transient AnchorPane anchorPane;
    int maxType = 1;
    int counter = 0;
    protected Random random;
    transient Timeline timeline;
    protected transient Playable playable;

    public ZombieGenerator(Grid grid, AnchorPane anchorPane, Playable playable) {
        this.grid = grid;
        this.anchorPane = anchorPane;
        this.playable = playable;
    }

    public ZombieGenerator() {}

    public void generateZombies() {
        random = new Random();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), event ->
        {
            if (counter < 0) {
                //wait
            }

            else if (counter < 10){
                if (counter%4 == 0){
                    generateZombie(maxType);
                }
            }

            else if (counter < 25) {
                if(counter%3 == 0) {
                    generateZombie(maxType);
                }
            }

            else if (counter == 25) {
                maxType++;
            }

            else if (counter < 50) {
                if (counter%3 == 0) {
                    generateZombie(maxType);
                }
            }

            else if (counter == 50 || counter == 52) {  // half game attack
                allRows(maxType);
                if (counter == 52){
                    maxType += 2;
                }
            }

            else if (counter == 75) {
                maxType++;
            }

            else if (counter < 100) {
                if (counter%3 == 0) {
                    generateZombie(maxType);
                }
            }

            else if (counter == 100 || counter == 102 || counter == 104) {  // end game attack
                allRows(maxType);
            }

            else {
                if (grid.noZombiesInMap()) {
                    playable.gameWon();
                    return;
                }
            }

            counter++;
//            System.out.println(counter);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    protected void allRows(int maxType) {
        System.out.println("all rows");
        for (int row = 0; row < 5; row++) {
            int type = random.nextInt(maxType) + 1;
//            System.out.println("generating zombies type: " + type + "row: " + row);

            switch (type) {
                case 1:
                    new SimpleZombie(grid, anchorPane, row);
                    break;

                case 2:
                    new ConeHeadZombie(grid, anchorPane, row);
                    break;

                case 3:
                    new BucketHeadZombie(grid, anchorPane, row);
                    break;

                case 4:
                    new ScreenDoorZombie(grid, anchorPane, row);
                    break;
                case 5:
                    new FootballZombie(grid, anchorPane, row);
                    break;
            }
        }
    }

    protected void generateZombie(int maxType) {
        int type = random.nextInt(maxType) + 1;   // 1: simple 2: conehead 3: screendoor 4: imp
        int row = random.nextInt(5);
//        System.out.println("generating zombies type: " + type + "row: " + row);
        switch (type) {
            case 1:
                new SimpleZombie(grid, anchorPane, row);
                break;

            case 2:
                new ConeHeadZombie(grid, anchorPane, row);
                break;

            case 3:
                new BucketHeadZombie(grid, anchorPane, row);
                break;

            case 4:
                new ScreenDoorZombie(grid, anchorPane, row);
                break;
            case 5:
                new FootballZombie(grid, anchorPane, row);
        }
    }

    public void stop() {
        timeline.pause();

    }

    public void resume() {
        timeline.play();
    }
}
