package testgroup.plantsvszombies.plants;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import testgroup.plantsvszombies.Grid;
import testgroup.plantsvszombies.Selector;

public class Sunflower extends Plant{
    protected Timeline timeline;
    private Selector selector;
    public static final int PRICE = 50;

    public Sunflower(Grid grid, StackPane stackPane, int row, int column, Selector selector) {
        super(grid, stackPane, row, column, "/plants/sunflower.gif");
        timeline = new Timeline(new KeyFrame(Duration.seconds(25), event -> produceSun()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        this.selector = selector;
    }

    private void produceSun() {
        new SunPoint(stackPane, selector);
    }

    @Override
    public void vanish() {
        System.out.println("vanishing");
        timeline.stop();
        grid.getPlantsList()[row][column] = null;
        stackPane.getChildren().remove(image);
    }


    @Override
    public int getEaten() {
        return 0;
    }

    @Override
    public int getX() {
        return 0;
    }

    public void stop() {
        // todo
    }

}
