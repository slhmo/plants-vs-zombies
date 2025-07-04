package testgroup.plantsvszombies.plants;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import testgroup.plantsvszombies.Grid;
import testgroup.plantsvszombies.Selector;
import testgroup.plantsvszombies.zombies.Zombie;

public class Sunflower extends Plant{
    protected transient Timeline timeline;
    private transient Selector selector;

    public Sunflower(Grid grid, StackPane stackPane, int row, int column, Selector selector) {
        super(grid, stackPane, row, column, "/plants/sunflower.gif", 4);
        timeline = new Timeline(new KeyFrame(Duration.seconds(15), event -> produceSun()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        this.selector = selector;
    }

    private void produceSun() {
        new SunPoint(stackPane, selector);
    }

    @Override
    public void stop() {
        timeline.pause();
    }

    @Override
    public void resume() {
        timeline.play();
    }

    @Override
    public void loadPlant(StackPane stackPane, Selector selector) {
        grid.removePlant(this);
        new Sunflower(grid, stackPane, row, column, selector);
    }


}
