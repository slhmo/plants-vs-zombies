package testgroup.plantsvszombies.plants;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import testgroup.plantsvszombies.Grid;
import testgroup.plantsvszombies.zombies.Zombie;

public class PeaShooter extends Plant{
    protected Timeline timeline;

    public PeaShooter(Grid grid, StackPane stackPane, int row, int column) {
        new PeaShooter(grid, stackPane, row, column, "/plants/peaShooter.gif", 4);
    }

    protected PeaShooter(Grid grid, StackPane stackPane, int row, int column, String imageUrl, int hp) {
        super(grid, stackPane, row, column, imageUrl, hp);
        timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> shoot()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    protected void shoot() {
        if (!grid.getZombies()[row].isEmpty())
            new Pea(this, stackPane, grid);
    }


    @Override
    public void plant(Grid grid, StackPane cell, int row, int column) {
        super.plant(grid, cell, row, column);
    }

    @Override
    public void stop() {
        timeline.pause();
    }

    @Override
    public void resume() {
        timeline.play();
    }

}
