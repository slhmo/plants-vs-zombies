package testgroup.plantsvszombies.zombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import testgroup.plantsvszombies.Grid;

import java.util.Random;

public class ZombieGenerator {
    private Grid grid;
    private AnchorPane anchorPane;
    private StackPane stackPane;
    int maxType = 1;
    int counter = 0;
    private Random random;
    Timeline timeline;

    public ZombieGenerator(Grid grid, AnchorPane anchorPane) {
        this.grid = grid;
        this.anchorPane = anchorPane;
    }

    public void generateZombies() {
        random = new Random();
        timeline = new Timeline(new KeyFrame(Duration.seconds(2), event ->
        {
            if (counter<15) {
                if(counter%3 == 0) {
                    generateZombie(maxType);
                }
            }

            else if (counter == 15) {
                maxType++;
            }

            else if (counter < 25) {
                if (counter%2 == 0) {
                    generateZombie(maxType);
                }
            }

            else if (counter < 33) {  // half game attack
                if (counter == 26 || counter == 29 || counter == 32) {
                    allRows(maxType);
                }
            }

            else if (counter == 33) {
                maxType++;
            }

            else if (counter < 40) {
                if (counter%2 == 0) {
                    generateZombie(maxType);
                }
            }

            else if (counter == 40) {
                maxType++;
            }

            else if (counter < 50) {
                if (counter%2 == 0) {
                    generateZombie(maxType);
                }
            }

            else if (counter < 60) {  // half game attack
                if (counter == 52 || counter == 54 || counter == 56 || counter == 58) {
                    allRows(maxType);
                }
            }

            else if (counter == 60) {
                // todo
            }

            counter++;
//            System.out.println(counter);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void allRows(int maxType) {
        System.out.println("all rows");
        for (int row = 0; row<5; row++) {
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
                    new ScreenDoorZombie(grid, anchorPane, row);
                    break;

                case 4:
                    new FootballZombie(grid, anchorPane, row);
                    break;
            }
        }
    }

    private void generateZombie(int maxType) {
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
                new ScreenDoorZombie(grid, anchorPane, row);
                break;

            case 4:
                new FootballZombie(grid, anchorPane, row);
                break;
        }
    }

    public void stop() {
        timeline.stop();
    }

    public void resume() {
        timeline.play();
    }
}
