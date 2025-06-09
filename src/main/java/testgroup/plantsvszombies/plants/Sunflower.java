package testgroup.plantsvszombies.plants;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import testgroup.plantsvszombies.Grid;
import testgroup.plantsvszombies.Selector;

public class Sunflower extends Plant{
    private Timeline timeline;
    private StackPane stackPane;
    private Selector selector;

    static {
        value = 50;
    }

    public Sunflower(Grid grid, StackPane stackPane, int row, int column, Selector selector) {
        super(grid, stackPane, row, column, selector);
        image = new ImageView(getClass().getResource("/plants/sunflower.gif").toString());
        plant(grid, stackPane, row, column, selector);
        timeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> produceSun()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        this.stackPane = stackPane;
        this.selector = selector;
    }

    private void produceSun() {
        new SunPoint(stackPane, selector);
        System.out.println(stackPane.getChildren());
    }

    @Override
    public int getEaten() {
        return 0;
    }

    @Override
    public int getX() {
        return 0;
    }
}
